package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Prodotto;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.ProdottoService;

@Component
public class ProdottoValidator implements Validator{

	@Autowired
	private ProdottoService prodottoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"prezzo", "required");
		
		if (!errors.hasErrors()) {
			if (this.prodottoService.alreadyExists((Prodotto)o)) {
				errors.reject("prodotto.duplicato");
			}
		}
	}

	public void validateModifica(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"prezzo", "required");
		
	}

}
