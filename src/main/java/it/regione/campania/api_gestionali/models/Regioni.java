package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "regioni")
public class Regioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idregione")
    private Integer idregione;

    @Size(max = 250)
    @NotNull
    @Column(name = "descrizione", nullable = false, length = 250)
    private String descrizione;

    @Column(name = "iniziovalidita")
    private LocalDate iniziovalidita;

    @Column(name = "finevalidita")
    private LocalDate finevalidita;

    @Size(max = 50)
    @Column(name = "codiceistat", length = 50)
    private String codiceistat;

    @Size(max = 3)
    @Column(name = "codice_archivio", length = 3)
    private String codiceArchivio;

    public @Size(max = 3) String getCodiceArchivio() {
        return codiceArchivio;
    }

    public Integer getId() {
        return idregione;
    }

    public void setId(Integer idregione) {
        this.idregione = idregione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Nazioni getNazioneid() {
        return null;
    }

    public void setNazioneid(Nazioni nazioneid) {
        return;
    }

    public LocalDate getIniziovalidita() {
        return iniziovalidita;
    }

    public void setIniziovalidita(LocalDate iniziovalidita) {
        this.iniziovalidita = iniziovalidita;
    }

    public LocalDate getFinevalidita() {
        return finevalidita;
    }

    public void setFinevalidita(LocalDate finevalidita) {
        this.finevalidita = finevalidita;
    }

    public String getCodiceistat() {
        return codiceistat;
    }

    public void setCodiceistat(String codiceistat) {
        this.codiceistat = codiceistat;
    }

}