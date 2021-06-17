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
import it.uniroma3.siw.spring.service.TavoloService;


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
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private Sala sala;
	
	@RequestMapping(value="/prenota", method = RequestMethod.GET)
	public String addPrenotazione(Model model) {
		logger.debug("*sale: "+this.salaService.tutti().toString());
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("sale", this.salaService.tutti());
		return "prenotaForm.html";
	}
	
	@RequestMapping(value = "/addPrenotazione", method = RequestMethod.POST)
	public String newCollezione(@RequestParam Long salaSelezionata, @ModelAttribute("prenotazione") Prenotazione prenotazione, Model model,BindingResult bindingResult) {
		sala = salaService.salaPerId(salaSelezionata);
		
		//i posti liberi nella sala vengono aggiornati nel validate
		this.prenotazioneValidator.validate(prenotazione,sala,bindingResult);
		if (!bindingResult.hasErrors()) {
			
			
			prenotazione.setTavolo(tavoloService.inserisci(new Tavolo(prenotazione.getPosti(),sala))); 
			this.prenotazioneService.inserisci(prenotazione);
			
			return "index.html";
		}
		return "prenotaForm.html";
	}
	
	@RequestMapping(value ="/deletePrenotazione/{id}",method = RequestMethod.GET)
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		this.prenotazioneService.cancella(id);
		return "index.html";
	}
}
