package it.uniroma3.siw.spring.model;

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

	//public static final Integer POSTI_MAX = 100;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int postiTotali;// = 10;//POSTI_MAX.intValue();
	
	@Column(nullable = false)
	private String nome;
	
	public int postiLiberi;
	
	@OneToMany(mappedBy ="sala",cascade = {CascadeType.REMOVE})
	private List<Tavolo> tavoli;

	
	
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

	public int getPostiLiberi() {
		return postiLiberi;
	}
	
	public void setPostiLiberi(int posti) {
		this.postiLiberi = posti;
	}
	
	public void riduciPostiLiberi(int postiPrenotazione) {
		this.postiLiberi -= postiPrenotazione;
	}
	
	
}
