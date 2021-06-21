package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"sala_id","data","ora"}))

public class SalaDataOra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	public int postiLiberi;
	
	@ManyToOne
	private Sala sala;
	@Column
	private LocalDate data;
	@Column
	private String ora;
	
	public SalaDataOra() {}
	public SalaDataOra(Sala sala, LocalDate data, String ora) {
		this.sala = sala;
		this.data = data;
		this.ora = ora;
	}

	public int getPostiLiberi() {
		return postiLiberi;
	}

	public void setPostiLiberi(int postiLiberi) {
		this.postiLiberi = postiLiberi;
	}
	public void riduciPostiLiberi(int postiPrenotazione) {
		this.postiLiberi -= postiPrenotazione;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}
	
	
}
