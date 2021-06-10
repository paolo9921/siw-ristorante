package it.uniroma3.siw.spring.model;

 

import java.util.List;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

 


@Entity
public class Ristorante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Column(nullable = false)
    private String Nome;
    
    //posti a sedere totali
    @Column
    private int posti;
    
    	@Column(nullable = false)
    private String Indirizzo;
    
    @Column(nullable = false)
    private String Telefono;
    
    @OneToMany (mappedBy = "ristorante")
    private List<Gestore> gestori;
    
    @OneToMany
    private List<Tavolo> tavoli;
    
    
    
    
    public int getPosti() {
		return posti;
	}


	public void setPosti(int posti) {
		this.posti = posti;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<Gestore> getGestori() {
		return gestori;
	}


	public void setGestori(List<Gestore> gestori) {
		this.gestori = gestori;
	}


	public List<Tavolo> getTavoli() {
		return tavoli;
	}


	public void setTavoli(List<Tavolo> tavoli) {
		this.tavoli = tavoli;
	}



    
    public Ristorante() {
        
    }
    
    
    public String getNome() {
        return Nome;
    }

 

    public void setNome(String nome) {
        Nome = nome;
    }

 

    public String getIndirizzo() {
        return Indirizzo;
    }

 

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

 

    public String getTelefono() {
        return Telefono;
    }

 

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

 


}
 