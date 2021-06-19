package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.service.SalaService;

@Component
public class SalaValidator implements Validator{

	@Autowired
	private SalaService salaService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postiTotali", "required");
		
		if (!errors.hasErrors()) {
			if (this.salaService.alreadyExists((Sala)o)) {
				errors.reject("sala.duplicato");
			}
		}
	}

}
