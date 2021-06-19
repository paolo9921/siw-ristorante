package it.uniroma3.siw.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Sala;

public interface SalaRepository extends CrudRepository<Sala,Long>{

	public Optional<Sala> findById(Long id);

	public List<Sala> findByNome(String string);

}
