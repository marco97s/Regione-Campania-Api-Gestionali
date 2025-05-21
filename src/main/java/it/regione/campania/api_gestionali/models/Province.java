package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprovincia")
    private Integer idprovincia;

    @Size(max = 250)
    @NotNull
    @Column(name = "descrizione", nullable = false, length = 250)
    private String descrizione;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "regioneid", nullable = false)
    private Regioni regioneid;

    @Column(name = "iniziovalidita")
    private LocalDate iniziovalidita;

    @Column(name = "finevalidita")
    private LocalDate finevalidita;

    @Size(max = 250)
    @Column(name = "codiceistat", length = 250)
    private String codiceistat;

    public Integer getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(Integer idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Regioni getRegioneid() {
        return regioneid;
    }

    public void setRegioneid(Regioni regioneid) {
        this.regioneid = regioneid;
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