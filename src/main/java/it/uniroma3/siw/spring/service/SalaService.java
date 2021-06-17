package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.repository.SalaRepository;

@Service
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;

	
	@Transactional
	public Sala inserisci(Sala s) {
		return salaRepository.save(s);
	}
	@Transactional
	public List<Sala> tutti() {
		return (List<Sala>) salaRepository.findAll();
	}
	
	
	
	@Transactional
	public Sala salaPerId(Long id) {
		Optional<Sala> optional = salaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	
}
