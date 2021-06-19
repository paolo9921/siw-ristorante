package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.SalaService;
import it.uniroma3.siw.spring.service.TavoloService;

@Controller
public class MainController {
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private TavoloService tavoloService;
	
	
	@RequestMapping(value="/contatti",method = RequestMethod.GET)
	public String getContatti(Model model) {
	return "contatti.html";
	}
	
	@RequestMapping(value="/admin/reset", method = RequestMethod.GET)
	public String resetSale(Model model) {
		List<Sala> sale = this.salaService.tutti();
		
		for(Sala s : sale) {
			s.setPostiLiberi(s.getPostiTotali());
			tavoloService.deleteAll();
		}
		return "admin/home.html";
	}
	
	
}
