package it.uniroma3.siw.spring.service;

import java.util.List;

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

	public List<Prodotto> tutti() {
		return (List<Prodotto>) prodottoRepository.findAll();
	}
}
