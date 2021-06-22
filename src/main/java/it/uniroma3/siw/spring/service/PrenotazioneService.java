package it.uniroma3.siw.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.PrenotazioneRepository;
import it.uniroma3.siw.spring.repository.SalaRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Autowired
	private SalaService salaService;
	
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

	@Transactional
	public void cancellaPerSala(Sala sala) {
		
	}

	@Transactional
	public List<Prenotazione> tuttiPerData(LocalDate data) {
		return (List<Prenotazione>) prenotazioneRepository.findAllByData(data);
	}
	@Transactional
	public List<Prenotazione> tuttiOrdinatiPerData() {
		return (List<Prenotazione>) prenotazioneRepository.findAllByDataOrderByData();
	}

	@Transactional
	public List<Prenotazione> tuttiPerSala(Long idSala) {
		
		return (List<Prenotazione>) prenotazioneRepository.findAllBySala(salaService.salaPerId(idSala));
	}
}
