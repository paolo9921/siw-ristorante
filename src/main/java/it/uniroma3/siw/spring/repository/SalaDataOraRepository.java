package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.SalaDataOra;

public interface SalaDataOraRepository extends CrudRepository<SalaDataOra, Long>{

	@Query("SELECT sdo FROM SalaDataOra sdo WHERE sdo.sala=?1")
	public SalaDataOra findAllBySalaDataOra(Sala s);

	public SalaDataOra findBySalaAndDataAndOra(Sala s, LocalDate d, String ora);

	public List<SalaDataOra> findAllByOrderByDataAsc();

	public List<SalaDataOra> findAllBySala(Sala sala);
	
	

}
