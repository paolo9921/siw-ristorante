package it.uniroma3.siw.spring.controller;

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

import it.uniroma3.siw.spring.controller.validator.SalaValidator;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.PrenotazioneService;
import it.uniroma3.siw.spring.service.SalaDataOraService;
import it.uniroma3.siw.spring.service.SalaService;

@Controller
public class SalaController {
	
	@Autowired
	private SalaValidator salaValidator;
	
	@Autowired
	private SalaService salaService;
	
	
	@Autowired
	private PrenotazioneService prenotazioneService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	 @RequestMapping(value = "/admin/sale", method = RequestMethod.GET)
	    public String getSale(Model model) {
	    	model.addAttribute("sale", this.salaService.tutti());
	    	//serve il new sala perche la form per aggiungere una nuova sala sta in questa pagina
	    	model.addAttribute("sala",new Sala());
	    	model.addAttribute("modif",false);
	    	return "admin/sale.html";
	    }
	
	
	/*@RequestMapping(value="/admin/addSala", method = RequestMethod.GET)
	public String addSala(Model model) {
		model.addAttribute("sala", new Sala());
		return "admin/salaForm.html";
	}*/
	
	
	@RequestMapping(value = "/admin/addSala", method = RequestMethod.POST)
	public String newSala(@ModelAttribute("sala") Sala sala, Model model,BindingResult bindingResult) {
		
		this.salaValidator.validate(sala,bindingResult);
		if (!bindingResult.hasErrors()) {
			this.salaService.inserisci(sala);
			model.addAttribute("sale",salaService.tutti());
			return "admin/sale.html";
		}
		model.addAttribute("sale", this.salaService.tutti());
		return "admin/sale.html";
	}
	
	@RequestMapping(value = "/admin/modificaSala", method = RequestMethod.POST)
	public String updateSala(@ModelAttribute("sala") Sala sala, Model model,BindingResult bindingResult) {
		
		this.salaValidator.validateModifica(sala,bindingResult);
		if (!bindingResult.hasErrors()) {
			this.salaService.inserisci(sala);
			model.addAttribute("sale",salaService.tutti());
			return "admin/sale.html";
		}
		model.addAttribute("sale", this.salaService.tutti());
		model.addAttribute("modif",true);
		return "admin/sale.html";
	}
	
	
	
   	@RequestMapping(value = "/sale/modifica/{id}", method = RequestMethod.GET)
	public String modificaSala(@PathVariable("id") Long id, Model model) {
   		model.addAttribute("sale", this.salaService.tutti());
		model.addAttribute("sala",this.salaService.salaPerId(id));
		model.addAttribute("modif",true);
		return "admin/sale.html";
	}
	
	
	
	@RequestMapping(value = "/sale/disattiva/{id}", method = RequestMethod.GET)
	public String disattivaSala(@PathVariable("id") Long id, Model model) {
		logger.debug("*********************************************qua**");
		this.salaService.salaPerId(id).setAttiva(false);
		model.addAttribute("sale",this.salaService.tutti());
		model.addAttribute("sala",new Sala());
		return "admin/sale.html";
	}
	
	@RequestMapping(value = "/sale/attiva/{id}", method = RequestMethod.GET)
	public String attivaSala(@PathVariable("id") Long id, Model model) {
		
		this.salaService.salaPerId(id).setAttiva(true);
		model.addAttribute("sale",this.salaService.tutti());
		model.addAttribute("sala",new Sala());

		return "admin/sale.html";
	}
	
}
