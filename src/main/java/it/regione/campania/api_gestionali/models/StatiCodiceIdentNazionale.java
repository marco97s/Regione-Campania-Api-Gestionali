package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;

@Entity
@Table(name = "staticodiceidentnazionale")
public class StatiCodiceIdentNazionale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstato")
    private Integer idstato;

    @Column(name = "descrizione")
    private String descrizione;

    public StatiCodiceIdentNazionale() {
    }

    public StatiCodiceIdentNazionale(Integer idstato, String descrizione) {
        this.idstato = idstato;
        this.descrizione = descrizione;
    }

    public Integer getIdstato() {
        return idstato;
    }

    public void setIdstato(Integer idstato) {
        this.idstato = idstato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
