package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.service.PrenotazioneService;


@Controller
public class PrenotazioneController {

	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	@RequestMapping(value="/prenota", method = RequestMethod.GET)
	public String addPrenotazione(Model model) {
		model.addAttribute("prenotazione", new Prenotazione());
		return "prenotaForm.html";
	}
	
	@RequestMapping(value = "/addPrenotazione", method = RequestMethod.POST)
	public String newCollezione(@ModelAttribute("prenotazione") Prenotazione prenotazione, Model model,BindingResult bindingResult) {
		this.prenotazioneValidator.validate(prenotazione, bindingResult);
		if (!bindingResult.hasErrors()) {
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
