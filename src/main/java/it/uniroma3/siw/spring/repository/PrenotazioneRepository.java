package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long>{
	
	public Optional<Prenotazione> findById(Long id);
	
	
}
