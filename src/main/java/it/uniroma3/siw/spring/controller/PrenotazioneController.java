package it.uniroma3.siw.spring.controller;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.Tavolo;

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
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.SalaService;


@Controller
public class PrenotazioneController {

	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	@Autowired
	private SalaService salaService;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private Sala sala;
	
	@RequestMapping(value="/prenota", method = RequestMethod.GET)
	public String addPrenotazione(Model model) {
		model.addAttribute("prenotazione", new Prenotazione());
		return "prenotaForm.html";
	}
	
	@RequestMapping(value = "/addPrenotazione", method = RequestMethod.POST)
	public String newCollezione(@ModelAttribute("prenotazione") Prenotazione prenotazione, Model model,BindingResult bindingResult) {
		this.sala = salaService.salaPerId(Long.valueOf(1));
		logger.debug("*****************************POSTI: "+prenotazione.getPosti()+".");
		this.prenotazioneValidator.validate(prenotazione,bindingResult);
		if (!bindingResult.hasErrors()) {
			
			prenotazione.setTavolo(new Tavolo(prenotazione.getPosti(),sala));
			this.prenotazioneService.inserisci(prenotazione);
			
			return "index.html";
		}
		return "prenotaForm.html";
	}
	
	@RequestMapping(value ="/deletePrenotazione/{id}",method = RequestMethod.GET)
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		this.prenotazioneService.cancella(this.prenotazioneService.prenotazionePerId(id));
		return "index.html";
	}
}
