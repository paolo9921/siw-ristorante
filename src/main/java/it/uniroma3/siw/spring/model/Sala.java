package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sala {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int postiTotali;
	
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy ="sala",cascade = {CascadeType.REMOVE})
	private List<Tavolo> tavoli;
	
	@OneToMany
	private List<SalaDataOra> saleDataOra;
	
	@OneToMany
	private List<Prenotazione> prenotazioni;
	
	private boolean attiva = true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getPostiTotali() {
		return postiTotali;
	}

	public void setPostiTotali(int postiTotali) {
		this.postiTotali = postiTotali;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Tavolo> getTavoli() {
		return tavoli;
	}

	public void setTavoli(List<Tavolo> tavoli) {
		this.tavoli = tavoli;
	}

	public List<SalaDataOra> getSaleDataOra() {
		return saleDataOra;
	}

	public void setSaleDataOra(List<SalaDataOra> saleDataOra) {
		this.saleDataOra = saleDataOra;
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public boolean isAttiva() {
		return attiva;
	}

	public void setAttiva(boolean attiva) {
		this.attiva = attiva;
	}

	/*
	public void riduciPostiLiberi(int postiPrenotazione) {
		this.postiLiberi -= postiPrenotazione;
	}*/
	
	
}
