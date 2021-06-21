package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long>{

	@Query("SELECT p FROM Prodotto p WHERE p.categoria='antipasto'")
	public List<Prodotto> findAllAntipasti();
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria='pizza'")
	public List<Prodotto> findAllPizze();
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria='bevanda'")
	public List<Prodotto> findAllBevande();

}
