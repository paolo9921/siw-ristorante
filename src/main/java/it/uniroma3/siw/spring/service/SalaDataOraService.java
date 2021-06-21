package it.uniroma3.siw.spring.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Sala;
import it.uniroma3.siw.spring.model.SalaDataOra;
import it.uniroma3.siw.spring.repository.SalaDataOraRepository;

@Service
public class SalaDataOraService {

	@Autowired
	private SalaDataOraRepository salaDataOraRepository;
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Transactional
	public SalaDataOra checkSalaDataOra(Sala s, LocalDate d, String ora) {
		logger.debug("++++++  "+salaDataOraRepository.findAllBySalaDataOra(s).getSala().getNome());
		return salaDataOraRepository.findAllBySalaDataOra(s);
	}

	@Transactional
	//se esiste ritorna l'oggetto (univoco perche la chiave è sala,data,ora; altrimenti null
	public SalaDataOra alreadyExists(Sala s, LocalDate d, String ora) {
		SalaDataOra sdo = (SalaDataOra) this.salaDataOraRepository.findBySalaAndDataAndOra(s,d,ora);
		logger.debug("++++++  "+salaDataOraRepository.findBySalaAndDataAndOra(s,d,ora));
		if (sdo != null)
			return sdo;
		else 
			return null;
	}

	public void inserisci(SalaDataOra salaDataOra) {
		salaDataOraRepository.save(salaDataOra);
		
	}
	
	@Transactional
	public List<SalaDataOra> tutti() {
		return (List<SalaDataOra>) salaDataOraRepository.findAllByOrderByDataAsc();
	}
}
