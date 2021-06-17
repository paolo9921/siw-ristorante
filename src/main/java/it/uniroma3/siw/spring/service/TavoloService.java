package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Tavolo;
import it.uniroma3.siw.spring.repository.TavoloRepository;

@Service
public class TavoloService {

	@Autowired
	private TavoloRepository tavoloRepository;
	
	@Transactional
	public Tavolo inserisci(Tavolo t) {
		return tavoloRepository.save(t);
	}
}
