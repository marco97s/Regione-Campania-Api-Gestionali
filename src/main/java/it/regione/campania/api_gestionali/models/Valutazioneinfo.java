package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "valutazioneinfo")
public class Valutazioneinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('valutazioneinfo_idvalutazione_seq'::regclass)")
    @Column(name = "idvalutazione", nullable = false)
    private Integer idvalutazione;

    @Column(name = "descrizione", length = Integer.MAX_VALUE)
    private String descrizione;

    public Integer getIdvalutazione() {
        return idvalutazione;
    }

    public void setIdvalutazione(Integer idvalutazione) {
        this.idvalutazione = idvalutazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}