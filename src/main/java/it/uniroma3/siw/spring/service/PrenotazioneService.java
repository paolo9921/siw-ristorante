package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;
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
	public Optional<Prenotazione> prenotazionePerId(Long id) {
		return this.prenotazioneRepository.findById(id);
	}
	@Transactional
	public void cancella(Long id) {	
		this.prenotazioneRepository.deleteById(id);
		
	}
	@Transactional
	public List<Prenotazione> tutti() {
		return (List<Prenotazione>) prenotazioneRepository.findAll();
	}
	
	@Transactional
	public List<User> utentiConPrenotazione(){
		return prenotazioneRepository.findAllUtentiId();
	}

}
