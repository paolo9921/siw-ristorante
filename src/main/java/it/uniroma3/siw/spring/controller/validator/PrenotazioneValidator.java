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

	
	public void validate(Prenotazione prenotazione, Sala sala,String orarioSelezionato, Errors errors) {
		
		if (prenotazione.getData().isBefore(LocalDate.now()) )
			errors.reject("prenotazione.data");	
		
		
		String ora;
		if( ((orarioSelezionato).compareTo("15"))<0 ) {
			ora = "pranzo";
		}else ora = "cena";
		logger.debug("***** ora: "+ora);
		SalaDataOra salaDataOra = salaDataOraService.alreadyExists(sala, prenotazione.getData(), ora);
		/*sala.riduciPostiLiberi(prenotazione.getPosti());
		if(sala.getPostiLiberi() < 0)
			errors.reject("pieno");*/
		if (salaDataOra != null) {
			salaDataOra.riduciPostiLiberi(prenotazione.getPosti());
			logger.debug("******* IF ");
			if(salaDataOra.getPostiLiberi() < 0) {
				//rimetto i posti senza questa prenotazione
				salaDataOra.riduciPostiLiberi(-prenotazione.getPosti());
				errors.reject("prenotazione.pieno");
			}
		}
		else{
			logger.debug("******* ELSE ");

			salaDataOra = new SalaDataOra(sala, prenotazione.getData(),ora);
			salaDataOra.setPostiLiberi(sala.getPostiTotali()-prenotazione.getPosti());
			salaDataOraService.inserisci(salaDataOra);
		}
		prenotazione.setSalaDataOra(salaDataOra);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
