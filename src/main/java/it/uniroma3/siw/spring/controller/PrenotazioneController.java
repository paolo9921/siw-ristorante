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

	private Prenotazione prenotazioneCorrente = null;
	
	@RequestMapping(value="/prenota", method = RequestMethod.GET)
	public String addPrenotazione(Model model) {
		logger.debug("******************pren corrente:"+prenotazioneCorrente);
		model.addAttribute("prenotazione", new Prenotazione());
		prenotazioneCorrente = null;
		//model.addAttribute("user",(userService.getUserCorrente()));
		//logger.debug("**********user: "+userService.getUserCorrente().toString() +"nome: "+userService.getUserCorrente().getNome());
		model.addAttribute("prenotazioni", this.prenotazioneService.tuttiPerDataDaOggiPerUtente(userService.getUserCorrente()));
		model.addAttribute("sale", this.salaService.tutti());
		
		return "prenota.html";
	}
	
	@RequestMapping(value = "/addPrenotazione", method = RequestMethod.POST)
	public String newPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione,  Model model,BindingResult bindingResult) {
		
		sala = salaService.salaPerId(prenotazione.getSala().getId());
		
		//i posti liberi nella sala vengono aggiornati nel validate
		this.prenotazioneValidator.validate(prenotazione,prenotazioneCorrente,bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			//alla prenotazione assegno un nuovo tavolo con posti= posti della prenotazione e la sala selezionata
			prenotazione.setTavolo(tavoloService.inserisci(new Tavolo(prenotazione.getPosti(),sala))); 
			prenotazione.setSala(sala);
			prenotazione.setUtente(userService.getUserCorrente());
			
			this.prenotazioneService.inserisci(prenotazione);
			model.addAttribute(prenotazione);
			
			return "prenotazioneConfermata.html";
		}
		model.addAttribute("sale", this.salaService.tutti());
		model.addAttribute("prenotazioni", this.prenotazioneService.tuttiPerDataDaOggiPerUtente(userService.getUserCorrente()));

		return "prenota.html";
	}
	
	
	
	@RequestMapping(value = "/modificaPrenotazione/{id}", method = RequestMethod.GET)
	public String modificaPrenotazione(@PathVariable("id") Long id, Model model) {
   		Prenotazione prenotazione = prenotazioneService.prenotazionePerId(id);
		if (prenotazione.getUtente() != userService.getUserCorrente())
			return "error.html";
		
		model.addAttribute("prenotazione",this.prenotazioneService.prenotazionePerId(id));
		model.addAttribute("prenotazioni", this.prenotazioneService.tuttiPerDataDaOggiPerUtente(userService.getUserCorrente()));
		model.addAttribute("sale", this.salaService.tutti());

		prenotazioneCorrente = prenotazione;
		
		return "prenota.html";
	}
	

	
	@RequestMapping(value ="/eliminaPrenotazione/{id}",method = RequestMethod.GET)
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		
		Prenotazione p = prenotazioneService.prenotazionePerId(id);
		p.getSalaDataOra().riduciPostiLiberi(-p.getPosti());
		
		this.prenotazioneService.cancella(id);
		
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("prenotazioni", this.prenotazioneService.tuttiPerDataDaOggiPerUtente(userService.getUserCorrente()));
		model.addAttribute("sale", this.salaService.tutti());
		return "prenota.html";
	}
	
	
	
	/*		ADMIN		*/
	
	
	@RequestMapping(value ="/admin/prenotazioni",method = RequestMethod.GET)
	public String getPrenotazioni(Model model) {
		model.addAttribute("salaDataOra", this.salaDataOraService.tutti());
		model.addAttribute("prenotazioni",this.prenotazioneService.tuttiPerDataDaOggi());
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
