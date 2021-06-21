package it.uniroma3.siw.spring.controller.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.SalaDataOra;
import it.uniroma3.siw.spring.repository.SalaDataOraRepository;
import it.uniroma3.siw.spring.service.SalaService;

@Component
public class SalaValidator implements Validator{

	@Autowired
	private SalaService salaService;
	
	@Autowired
	private SalaDataOraRepository salaDataOraRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postiTotali", "required");
		
		if( ((Sala)o).getPostiTotali() < 1)
			errors.reject("sala.postiNegativi");
		
		if (!errors.hasErrors()) {
			if (this.salaService.alreadyExists((Sala)o)) {
				errors.reject("sala.duplicato");
			}
		}
	}

	public void validateModifica(Sala sala, Errors errors) {
		boolean errore=false;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postiTotali", "required");
		
		if( sala.getPostiTotali() < 1)
			errors.reject("sala.postiNegativi");
		
		//prendo tutte le SaleDataOra con la sala corrente e controllo se posso diminuire i posti 
		List<SalaDataOra> tutteSdo = salaDataOraRepository.findAllBySala(sala);
		for (SalaDataOra salaDataOraCorrente : tutteSdo) {
			
			//if controlla se i nuovi posti sono sufficenti per le prenotazioni gia effettuate
			if( (salaDataOraCorrente.getSala().getPostiTotali() - salaDataOraCorrente.getPostiLiberi()) > sala.getPostiTotali()) {
				errors.reject("sala.pochiPosti");
				errore=true;
				break;
			}
		}
		if(!errore) {
			//aggiorno i posti liberi di ogni salaDataOra
			for (SalaDataOra salaDataOraCorrente : tutteSdo) {
				int postiOccupati = salaDataOraCorrente.getSala().getPostiTotali() - salaDataOraCorrente.getPostiLiberi();
				salaDataOraCorrente.setPostiLiberi(sala.getPostiTotali() - postiOccupati );
				
				}
		}
		
	}
	

}
