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
		
		// controllo se la data è prima di oggi
		if (prenotazione.getData().isBefore(LocalDate.now()) )
			errors.reject("prenotazione.data");	
		
		// pranzo o cena
		String ora;
		if( ((prenotazione.getOrario()).compareTo("15"))<0 ) {
			ora = "pranzo";
		}else ora = "cena";
		
		SalaDataOra salaDataOra = salaDataOraService.alreadyExists(prenotazione.getSala(), prenotazione.getData(), ora);
		
		if(salaDataOra != null) {
			
			// sto in modifica
			if (prenotazioneCorrente != null) {
				
				prenotazioneCorrente.getSalaDataOra().riduciPostiLiberi(-prenotazioneCorrente.getPosti());
							
				if( (salaDataOra.getData().isEqual(prenotazioneCorrente.getData()))&& (salaDataOra.getOra().equals(prenotazioneCorrente.getSalaDataOra().getOra()) ) && (salaDataOra.getSala().getNome().equals(prenotazioneCorrente.getSala().getNome())) ) {
					salaDataOra.setPostiLiberi(prenotazioneCorrente.getSalaDataOra().getPostiLiberi());
					logger.debug("STO NELL'IF");
				}
				
				if(salaDataOra.getPostiLiberi() < prenotazione.getPosti()) {
					prenotazioneCorrente.getSalaDataOra().riduciPostiLiberi(prenotazioneCorrente.getPosti());
					if( (salaDataOra.getData().isEqual(prenotazioneCorrente.getData()))&& (salaDataOra.getOra().equals(prenotazioneCorrente.getSalaDataOra().getOra()) ) && (salaDataOra.getSala().getNome().equals(prenotazioneCorrente.getSala().getNome())) )
						salaDataOra.setPostiLiberi(prenotazioneCorrente.getSalaDataOra().getPostiLiberi());
				}
			}
			
			if(salaDataOra.getPostiLiberi() < prenotazione.getPosti()) 
				errors.reject("prenotazione.pieno");
			
			// ci sono sufficenti posti  (sia in aggiungi che in modifica)
			else {
				salaDataOra.riduciPostiLiberi(prenotazione.getPosti());
				prenotazione.setSalaDataOra(salaDataOra);
			}
		}
		
		//nuova salaDataOra
		else {
			SalaDataOra nuovaSalaDataOra = new SalaDataOra(prenotazione.getSala(), prenotazione.getData(),ora);
			if (prenotazione.getSala().getPostiTotali() < prenotazione.getPosti())
				errors.reject("prenotazione.pieno");
			
			else {
				nuovaSalaDataOra.setPostiLiberi(prenotazione.getSala().getPostiTotali() - prenotazione.getPosti());
				prenotazione.setSalaDataOra(nuovaSalaDataOra);
				if (prenotazioneCorrente != null)
					prenotazioneCorrente.getSalaDataOra().riduciPostiLiberi(-prenotazioneCorrente.getPosti());
				salaDataOraService.inserisci(nuovaSalaDataOra);
			}
		}
		
		
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
