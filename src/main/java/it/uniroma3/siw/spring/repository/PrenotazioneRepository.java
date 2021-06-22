package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.Tavolo;
import it.uniroma3.siw.spring.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long>{
	
	public Optional<Prenotazione> findById(Long id);
	
	//restituisce tutti gli utenti (id) presenti nella tabella prenotazione
	@Query("SELECT p.utente FROM Prenotazione p")
	public List<User> findAllUtentiId();

	@Query("SELECT p FROM Prenotazione p WHERE data=?1")
	public List<Prenotazione> findAllByData(LocalDate data);
	
	@Query("SELECT p FROM Prenotazione p ORDER BY p.data ASC, p.orario ASC")
	public List<Prenotazione> findAllByDataOrderByData();
	
	public List<Prenotazione> findAllBySala(Sala salaPerId);

	@Query("SELECT p FROM Prenotazione p WHERE p.data >=?1 ORDER BY p.data ASC, p.orario ASC")
	public List<Prenotazione> findAllByDataFromToday(LocalDate now);
	
	
	
}
