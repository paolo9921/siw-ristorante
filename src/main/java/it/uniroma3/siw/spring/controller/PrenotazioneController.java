package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.PrenotazioneService;
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
	private CredentialsService credentialsService;
	
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
	public String newPrenotazione(@RequestParam Long salaSelezionata, @ModelAttribute("prenotazione") Prenotazione prenotazione, Model model,BindingResult bindingResult) {
		sala = salaService.salaPerId(salaSelezionata);
		//controllare data
		//i posti liberi nella sala vengono aggiornati nel validate
		this.prenotazioneValidator.validate(prenotazione,sala,bindingResult);
		if (!bindingResult.hasErrors()) {
			
			//alla prenotazione assegno un nuovo tavolo con posti= posti della prenotazione e la sala selezionata
			prenotazione.setTavolo(tavoloService.inserisci(new Tavolo(prenotazione.getPosti(),sala))); 
			prenotazione.setUtente(userService.getUserCorrente());
			logger.debug("**********user: "+userService.getUserCorrente().toString() +"nome: "+userService.getUserCorrente().getNome());
			
			this.prenotazioneService.inserisci(prenotazione);
			
			return "index.html";
		}
		return "prenota.html";
	}
	
	@RequestMapping(value ="/deletePrenotazione/{id}",method = RequestMethod.GET)
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		//aumentare posti nella sala
		//this.salaService.salaPerId(  (Prenotazione)prenotazioneService.prenotazionePerId(id)    )
		this.prenotazioneService.cancella(id);
		
		return "index.html";
	}
	
	
	
	/*		ADMIN		*/
	
	@RequestMapping(value ="/admin/prenotazioni",method = RequestMethod.GET)
	public String getPrenotazioni(Model model) {
		logger.debug("*****************sto qua");
		model.addAttribute("prenotazioni",this.prenotazioneService.tutti());
		model.addAttribute("utenti",prenotazioneService.utentiConPrenotazione());
		return "admin/prenotazioni.html";
	}
	
	
	
}
