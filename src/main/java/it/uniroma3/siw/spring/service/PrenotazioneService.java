package it.uniroma3.siw.spring.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Tavolo;
import it.uniroma3.siw.spring.repository.PrenotazioneRepository;


@Service
public class PrenotazioneService {
	@Autowired 
	private PrenotazioneRepository prenotazioneRepository;
	
	
	@Transactional 
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}


	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> optional = prenotazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public void cancella(Prenotazione prenotazione) {
		prenotazioneRepository.deleteById(prenotazione.getId());
	}
}
