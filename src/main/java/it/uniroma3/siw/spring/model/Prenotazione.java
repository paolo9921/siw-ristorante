package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Prenotazione {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	 @OneToOne
	 private Tavolo tavolo;
	 
	 private String nome;
	 
	 @Column
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 private LocalDate data;
	 
	 @Column
	 private String orario;
	 
	 private int posti;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public int getPosti() {
		return posti;
	}

	public void setPosti(int posti) {
		this.posti = posti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
