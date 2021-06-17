package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import it.uniroma3.siw.spring.model.Sala;

public interface SalaRepository {

	public Optional<Sala> findById(Long id);

}
