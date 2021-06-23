package it.uniroma3.siw.spring.controller.validator;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.SalaDataOra;
import it.uniroma3.siw.spring.service.SalaDataOraService;

@Component
public class PrenotazioneValidator implements Validator{

	@Autowired
	private SalaDataOraService salaDataOraService;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Prenotazione prenotazione,Prenotazione prenotazioneCorrente, Errors errors) {
		if(prenotazioneCorrente != null) {
			logger.debug("+STO NEL VALIDATE pre corrente: "+prenotazioneCorrente.getPosti());
			prenotazioneCorrente.getSalaDataOra().riduciPostiLiberi(-prenotazioneCorrente.getPosti());
		}
		
		if (prenotazione.getData().isBefore(LocalDate.now()) )
			errors.reject("prenotazione.data");	
			
		String ora;
		if( ((prenotazione.getOrario()).compareTo("15"))<0 ) {
			ora = "pranzo";
		}else ora = "cena";
		logger.debug("***** ora: "+ora);
		SalaDataOra salaDataOra = salaDataOraService.alreadyExists(prenotazione.getSala(), prenotazione.getData(), ora);
		
		//gia esiste una prenotazione per la stessa sala nella stessa data
		if (salaDataOra != null) {
			logger.debug("*******riduco di: "+prenotazione.getPosti());
			salaDataOra.riduciPostiLiberi(prenotazione.getPosti());
			
			if(salaDataOra.getPostiLiberi() < 0) {
				logger.debug("*******posti minori di 0 quindi aumento di: "+prenotazione.getPosti());
				salaDataOra.riduciPostiLiberi(-prenotazione.getPosti());
				salaDataOra.riduciPostiLiberi(-prenotazioneCorrente.getPosti());
				errors.reject("prenotazione.pieno");				
			}
		}
		else{
			logger.debug("******* ELSE ");

			salaDataOra = new SalaDataOra(prenotazione.getSala(), prenotazione.getData(),ora);
			salaDataOra.setPostiLiberi(prenotazione.getSala().getPostiTotali()-prenotazione.getPosti());
			salaDataOraService.inserisci(salaDataOra);
		}
		prenotazione.setSalaDataOra(salaDataOra);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
