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

import it.uniroma3.siw.spring.controller.validator.SalaValidator;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.SalaService;

@Controller
public class SalaController {
	
	@Autowired
	private SalaValidator salaValidator;
	
	@Autowired
	private SalaService salaService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	 @RequestMapping(value = "/admin/sale", method = RequestMethod.GET)
	    public String getSale(Model model) {
	    	model.addAttribute("sale", this.salaService.tutti());
	    	return "admin/sale.html";
	    }
	
	
	@RequestMapping(value="/admin/addSala", method = RequestMethod.GET)
	public String addSala(Model model) {
		model.addAttribute("sala", new Sala());
		return "admin/salaForm.html";
	}
	
	
	@RequestMapping(value = "/admin/addSala", method = RequestMethod.POST)
	public String newSala(@ModelAttribute("sala") Sala sala, Model model,BindingResult bindingResult) {
		
		this.salaValidator.validate(sala,bindingResult);
		if (!bindingResult.hasErrors()) {
			this.salaService.inserisci(sala);
			model.addAttribute("sale",salaService.tutti());
			return "admin/sale.html";
		}
		return "admin/salaForm.html";
	}
	
	@RequestMapping(value = "/sale/delete/{id}", method = RequestMethod.GET)
	public String deleteSala(@PathVariable("id") Long id, Model model) {
		
		this.salaService.cancella(id);
		model.addAttribute("sale",this.salaService.tutti());
		return "admin/sale.html";
	}
	
}
