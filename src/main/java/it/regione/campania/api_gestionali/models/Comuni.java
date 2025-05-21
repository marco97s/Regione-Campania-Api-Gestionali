package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "comuni")
public class Comuni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomune")
    private Integer idcomune;

    @Size(max = 250)
    @NotNull
    @Column(name = "descrizione", nullable = false, length = 250)
    private String descrizione;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "provinciaid", nullable = false)
    private Province provinciaid;

    @Column(name = "iniziovalidita")
    private LocalDateTime iniziovalidita;

    @Column(name = "finevalidita")
    private LocalDateTime finevalidita;

    @Size(max = 250)
    @Column(name = "codistat", length = 250)
    private String codistat;

    @Column(name = "loc_id")
    private Integer locId;

    public Integer getLocId() {
        return locId;
    }

    public Integer getIdcomune() {
        return idcomune;
    }

    public void setIdcomune(Integer idcomune) {
        this.idcomune = idcomune;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Province getProvinciaid() {
        return provinciaid;
    }

    public void setProvinciaid(Province provinciaid) {
        this.provinciaid = provinciaid;
    }

    public LocalDateTime getIniziovalidita() {
        return iniziovalidita;
    }

    public void setIniziovalidita(LocalDateTime iniziovalidita) {
        this.iniziovalidita = iniziovalidita;
    }

    public LocalDateTime getFinevalidita() {
        return finevalidita;
    }

    public void setFinevalidita(LocalDateTime finevalidita) {
        this.finevalidita = finevalidita;
    }

    public String getCodistat() {
        return codistat;
    }

    public void setCodistat(String codistat) {
        this.codistat = codistat;
    }

}