
package it.uniroma3.siw.spring.model;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
 
@Entity
public class Tavolo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private int posti;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Prenotazione prenotazione;
    
    @ManyToOne
    private Sala sala;

    public Tavolo() {}
    
    public Tavolo(int posti, Sala sala) {
    	this.posti=posti;
    	this.sala=sala;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPosti() {
		return posti;
	}

	public void setPosti(int posti) {
		this.posti = posti;
	}


	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
}