package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.ProdottoValidator;
import it.uniroma3.siw.spring.model.Prodotto;
import it.uniroma3.siw.spring.service.ProdottoService;

@Controller
public class ProdottoController {
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private ProdottoValidator prodottoValidator;

	
	@RequestMapping(value="/menu",method = RequestMethod.GET)
	public String getMenu(Model model) {
		model.addAttribute("prodotti",this.prodottoService.tutti());
		return "menu.html";
	}
	
	
	
	
	
	@RequestMapping(value="/admin/addProdotto", method = RequestMethod.GET)
	public String addProdotto(Model model) {
		model.addAttribute("prodotto", new Prodotto());
		model.addAttribute("prodotti",this.prodottoService.tutti());
		model.addAttribute("modif",false);

		return "admin/prodotti.html";
	}
	
	@RequestMapping(value = "/admin/addProdotto", method = RequestMethod.POST)
	public String newProdotto(@ModelAttribute("prodotto") Prodotto prodotto, Model model,BindingResult bindingResult) {
		
		this.prodottoValidator.validate(prodotto,bindingResult);
		if (!bindingResult.hasErrors()) {
			this.prodottoService.inserisci(prodotto);
			
			model.addAttribute("prodotti",prodottoService.tutti());
			model.addAttribute("modif",false);
			return "admin/prodotti.html";
		}
		model.addAttribute("prodotti",prodottoService.tutti());
		model.addAttribute("modif",false);
		return "admin/prodotti.html";
	}
	@RequestMapping(value = "/admin/modificaProdotto", method = RequestMethod.POST)
	public String modificaProdotto(@ModelAttribute("prodotto") Prodotto prodotto, Model model,BindingResult bindingResult) {
		
		this.prodottoValidator.validateModifica(prodotto,bindingResult);
		if (!bindingResult.hasErrors()) {
			this.prodottoService.inserisci(prodotto);
			
			model.addAttribute("prodotti",prodottoService.tutti());
			model.addAttribute("modif",false);
			return "admin/prodotti.html";
		}
		model.addAttribute("prodotti",prodottoService.tutti());
		model.addAttribute("modif",false);
		return "admin/prodotti.html";
	}
	
	
   	@RequestMapping(value = "/prodotti/modifica/{id}", method = RequestMethod.GET)
	public String modificaSala(@PathVariable("id") Long id, Model model) {
   		model.addAttribute("prodotti", this.prodottoService.tutti());
		model.addAttribute("prodotto",this.prodottoService.prodottoPerId(id));
		model.addAttribute("modif",true);
		
		return "admin/prodotti.html";
	}
	
	
	
	@RequestMapping(value="/prodotti/cancella/{id}", method = RequestMethod.GET)
	public String cancProdotto(@PathVariable("id") Long id, Model model) {
		prodottoService.cancellaPerId(id);
		model.addAttribute("prodotti", this.prodottoService.tutti());
		model.addAttribute("prodotto", new Prodotto());
		model.addAttribute("modif",false);

		return "admin/prodotti.html";
	}
	
}
