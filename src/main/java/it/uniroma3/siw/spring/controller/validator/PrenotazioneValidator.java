package it.uniroma3.siw.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;

@Component
public class PrenotazioneValidator implements Validator{


	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void validate(Prenotazione prenotazione, Sala sala, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		sala.setPostiLiberi(prenotazione.getPosti());
		if(sala.getPostiLiberi() < 0)
			errors.reject("pieno");
			
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
