package it.uniroma3.siw.spring.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.Tavolo;
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.SalaDataOraService;
import it.uniroma3.siw.spring.service.SalaService;
import it.uniroma3.siw.spring.service.TavoloService;
import it.uniroma3.siw.spring.service.UserService;


@Controller
public class PrenotazioneController {

	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SalaDataOraService salaDataOraService;
	
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private Sala sala;

	
	
	@RequestMapping(value="/prenota", method = RequestMethod.GET)
	public String addPrenotazione(Model model) {
		
		model.addAttribute("prenotazione", new Prenotazione());
		
		//model.addAttribute("user",(userService.getUserCorrente()));
		//logger.debug("**********user: "+userService.getUserCorrente().toString() +"nome: "+userService.getUserCorrente().getNome());
		
		model.addAttribute("sale", this.salaService.tutti());
		return "prenota.html";
	}
	
	@RequestMapping(value = "/addPrenotazione", method = RequestMethod.POST)
	public String newPrenotazione(@RequestParam String orarioSelezionato,@RequestParam Long salaSelezionata, @ModelAttribute("prenotazione") Prenotazione prenotazione, Model model,BindingResult bindingResult) {
		sala = salaService.salaPerId(salaSelezionata);
		//controllare data
		//i posti liberi nella sala vengono aggiornati nel validate
		this.prenotazioneValidator.validate(prenotazione,sala,orarioSelezionato,bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			//alla prenotazione assegno un nuovo tavolo con posti= posti della prenotazione e la sala selezionata
			prenotazione.setTavolo(tavoloService.inserisci(new Tavolo(prenotazione.getPosti(),sala))); 
			prenotazione.setSala(sala);
			prenotazione.setUtente(userService.getUserCorrente());
			prenotazione.setOrario(orarioSelezionato);
			
			this.prenotazioneService.inserisci(prenotazione);
			model.addAttribute(prenotazione);
			return "prenotazioneConfermata.html";
		}
		model.addAttribute("sale", this.salaService.tutti());
		return "prenota.html";
	}
	
	@RequestMapping(value ="/prenotazioni/deletePrenotazione/{id}",method = RequestMethod.GET)
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		
		Prenotazione p = prenotazioneService.prenotazionePerId(id);
		//riduco i posti nella sala
		//p.getTavolo().getSala().riduciPostiLiberi(-p.getPosti());
		this.prenotazioneService.cancella(id);
		
		model.addAttribute("prenotazioni",prenotazioneService.tutti());
		return "admin/prenotazioni.html";
	}
	
	
	
	/*		ADMIN		*/
	
	
	@RequestMapping(value ="/admin/prenotazioni",method = RequestMethod.GET)
	public String getPrenotazioni(Model model) {
		model.addAttribute("salaDataOra", this.salaDataOraService.tutti());
		model.addAttribute("prenotazioni",this.prenotazioneService.tuttiOrdinatiPerData());
		model.addAttribute("utenti",prenotazioneService.utentiConPrenotazione());
		model.addAttribute("tavoli",tavoloService.tutti());
		model.addAttribute("sale",salaService.tutti());
		model.addAttribute("visualizzaSala",null);
		
		return "admin/prenotazioni.html";
	}
	
	
	@RequestMapping(value ="/admin/prenotazioniData",method = RequestMethod.GET)
	public String getPrenotazioniPerData(@RequestParam("dataSelezionata")@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate data,Model model) {
		logger.debug("******* STO QUA*****");
		model.addAttribute("salaDataOra", this.salaDataOraService.tutti());
		model.addAttribute("prenotazioni",this.prenotazioneService.tuttiPerData(data));
		model.addAttribute("utenti",prenotazioneService.utentiConPrenotazione());
		model.addAttribute("tavoli",tavoloService.tutti());
		model.addAttribute("sale",salaService.tutti());
		
		return "admin/prenotazioni.html";
	}
	@RequestMapping(value ="/admin/prenotazioniSala",method = RequestMethod.GET)
	public String getPrenotazioniPerSala(@RequestParam("salaSelezionata")Long idSala,Model model) {
		logger.debug("******* STO QUA*****");
		model.addAttribute("salaDataOra", this.salaDataOraService.tutti());
		model.addAttribute("prenotazioni",this.prenotazioneService.tuttiPerSala(idSala));
		model.addAttribute("utenti",prenotazioneService.utentiConPrenotazione());
		model.addAttribute("tavoli",tavoloService.tutti());
		model.addAttribute("sale",salaService.tutti());
		model.addAttribute("visualizzaSala",salaService.salaPerId(idSala));
		
		return "admin/prenotazioni.html";
	}
	
	
	
	
}
