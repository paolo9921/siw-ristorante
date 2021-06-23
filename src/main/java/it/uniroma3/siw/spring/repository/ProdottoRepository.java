package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long>{

	@Query("SELECT p FROM Prodotto p WHERE p.categoria='Antipasto'")
	public List<Prodotto> findAllAntipasti();
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria='Pizza'")
	public List<Prodotto> findAllPizze();
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria='Dolce'")
	public List<Prodotto> findAllDolci();
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria='Bevanda'")
	public List<Prodotto> findAllBevande();

	public List<Prodotto> findByNome(String nome);

	

}
