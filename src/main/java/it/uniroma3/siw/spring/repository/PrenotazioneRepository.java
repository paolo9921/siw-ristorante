package it.uniroma3.siw.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long>{
	
	public Optional<Prenotazione> findById(Long id);
	
	//restituisce tutti gli utenti (id) presenti nella tabella prenotazione
	@Query("SELECT p.utente FROM Prenotazione p")
	public List<User> findAllUtentiId();
	
}
