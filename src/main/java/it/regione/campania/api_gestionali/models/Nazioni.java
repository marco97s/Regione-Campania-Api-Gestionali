package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "nazioni")
public class Nazioni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnazione")
    private Integer idnazione;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "codeiso3")
    private String codeiso3;

    @Column(name = "continente")
    private String continente;

    @Column(name = "prefissotel")
    private String prefissotel;

    @Column(name = "moneta")
    private  String moneta;
    @Size(max = 3)

    @Column(name = "codice_archivio", length = 3)
    private String codiceArchivio;

    @Column(name = "codice_ept")
    private Integer codiceEpt;

    public @Size(max = 3) String getCodiceArchivio() {
        return codiceArchivio;
    }


    public Nazioni() {
    }

    public Nazioni(Integer idnazione, String descrizione, String codeiso3, String continente, String prefissotel, String moneta) {
        this.idnazione = idnazione;
        this.descrizione = descrizione;
        this.codeiso3 = codeiso3;
        this.continente = continente;
        this.prefissotel = prefissotel;
        this.moneta = moneta;
    }


    public Integer getIdnazione() {
        return idnazione;
    }

    public void setIdnazione(Integer idnazione) {
        this.idnazione = idnazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodeiso3() {
        return codeiso3;
    }

    public void setCodeiso3(String codeiso3) {
        this.codeiso3 = codeiso3;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getPrefissotel() {
        return prefissotel;
    }

    public void setPrefissotel(String prefissotel) {
        this.prefissotel = prefissotel;
    }

    public String getMoneta() {
        return moneta;
    }

    public void setMoneta(String moneta) {
        this.moneta = moneta;
    }

    public Integer getCodiceEpt() {
        return codiceEpt;
    }

    public void setCodiceEpt(Integer codiceEpt) {
        this.codiceEpt = codiceEpt;
    }
}
