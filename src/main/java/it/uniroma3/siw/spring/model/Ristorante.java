package it.uniroma3.siw.spring.model;

 

import java.util.List;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

 


@Entity
public class Ristorante {
    @Column(nullable = false)
    private String Nome;
    
    @Column(nullable = false)
    private String Indirizzo;
    
    @Column(nullable = false)
    private String Telefono;
    
    @OneToOne (mappedBy = "ristorante")
    private Gestore gestore;
    
    @OneToMany
    private List<Tavolo> tavoli;
    
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
 