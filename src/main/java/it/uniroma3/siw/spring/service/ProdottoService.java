package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Prodotto;
import it.uniroma3.siw.spring.repository.ProdottoRepository;

@Service
public class ProdottoService {

	@Autowired
	private ProdottoRepository prodottoRepository;
	
	@Transactional
	public Prodotto inserisci(Prodotto prodotto) {
		return prodottoRepository.save(prodotto);
		
	}
	@Transactional
	public List<Prodotto> tutti() {
		return (List<Prodotto>) prodottoRepository.findAll();
	}

	@Transactional
	public List<Prodotto> tuttiAntipasti(){
		return prodottoRepository.findAllAntipasti();
	}
	@Transactional
	public List<Prodotto> tuttiPizze(){
		return prodottoRepository.findAllPizze();
	}
	@Transactional
	public List<Prodotto> tuttiDolci(){
		return prodottoRepository.findAllDolci();
	}
	@Transactional
	public List<Prodotto> tuttiBevande(){
		return prodottoRepository.findAllBevande();
	}
	@Transactional
	public void cancellaTutti(){
		 prodottoRepository.deleteAll();
	}
	@Transactional
	public void cancellaPerId(Long id){
		prodottoRepository.deleteById(id);
	}
	@Transactional
	public Prodotto prodottoPerId(Long id) {
		Optional<Prodotto> optional = prodottoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

}
