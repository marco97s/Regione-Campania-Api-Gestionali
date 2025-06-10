package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "strutturericettiveinfo")
public class Strutturericettiveinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('strutturericettiveinfo_idstrutturaricettivainfo_seq'::regclass)")
    @Column(name = "idstrutturaricettivainfo", nullable = false)
    private Integer idstrutturaricettivainfo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "strutturaricettivaid", nullable = false)
    private StruttureRicettive strutturaricettivaid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ownerstrutturaid", nullable = false)
    private ProfiliUtente ownerstrutturaid;

    @Size(max = 250)
    @Column(name = "website", length = 250)
    private String website;

    @Size(max = 250)
    @Column(name = "twitter", length = 250)
    private String twitter;

    @Size(max = 250)
    @Column(name = "facebook", length = 250)
    private String facebook;

    @Column(name = "strutturatermale")
    private Boolean strutturatermale;

    @Column(name = "salaconferenze")
    private Boolean salaconferenze;

    @Column(name = "salaconferenzeposti")
    private Integer salaconferenzeposti;

    @Column(name = "numlavoratori")
    private Integer numlavoratori;

    @Column(name = "numdipendenti")
    private Integer numdipendenti;

    @Column(name = "numlavoratoristagionali")
    private Integer numlavoratoristagionali;

    @Column(name = "numstanzeperdisabili")
    private Integer numstanzeperdisabili;

    @Column(name = "numbagni")
    private Integer numbagni;

    @Column(name = "annocostruzione")
    private Integer annocostruzione;

    @Column(name = "annoristrutturazione")
    private Integer annoristrutturazione;

    @Size(max = 1000)
    @Column(name = "altreinfo", length = 1000)
    private String altreinfo;

    @Column(name = "creditcard")
    private Boolean creditcard;

    @Size(max = 4000)
    @Column(name = "note", length = 4000)
    private String note;

    @Size(max = 250)
    @Column(name = "periodobassastagione", length = 250)
    private String periodobassastagione;

    @Size(max = 250)
    @Column(name = "periodoaltastagione", length = 250)
    private String periodoaltastagione;

    public Integer getIdstrutturaricettivainfo() {
        return idstrutturaricettivainfo;
    }

    public void setIdstrutturaricettivainfo(Integer idstrutturaricettivainfo) {
        this.idstrutturaricettivainfo = idstrutturaricettivainfo;
    }

    public StruttureRicettive getStrutturaricettivaid() {
        return strutturaricettivaid;
    }

    public void setStrutturaricettivaid(StruttureRicettive strutturaricettivaid) {
        this.strutturaricettivaid = strutturaricettivaid;
    }

    public ProfiliUtente getOwnerstrutturaid() {
        return ownerstrutturaid;
    }

    public void setOwnerstrutturaid(ProfiliUtente ownerstrutturaid) {
        this.ownerstrutturaid = ownerstrutturaid;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Boolean getStrutturatermale() {
        return strutturatermale;
    }

    public void setStrutturatermale(Boolean strutturatermale) {
        this.strutturatermale = strutturatermale;
    }

    public Boolean getSalaconferenze() {
        return salaconferenze;
    }

    public void setSalaconferenze(Boolean salaconferenze) {
        this.salaconferenze = salaconferenze;
    }

    public Integer getSalaconferenzeposti() {
        return salaconferenzeposti;
    }

    public void setSalaconferenzeposti(Integer salaconferenzeposti) {
        this.salaconferenzeposti = salaconferenzeposti;
    }

    public Integer getNumlavoratori() {
        return numlavoratori;
    }

    public void setNumlavoratori(Integer numlavoratori) {
        this.numlavoratori = numlavoratori;
    }

    public Integer getNumdipendenti() {
        return numdipendenti;
    }

    public void setNumdipendenti(Integer numdipendenti) {
        this.numdipendenti = numdipendenti;
    }

    public Integer getNumlavoratoristagionali() {
        return numlavoratoristagionali;
    }

    public void setNumlavoratoristagionali(Integer numlavoratoristagionali) {
        this.numlavoratoristagionali = numlavoratoristagionali;
    }

    public Integer getNumstanzeperdisabili() {
        return numstanzeperdisabili;
    }

    public void setNumstanzeperdisabili(Integer numstanzeperdisabili) {
        this.numstanzeperdisabili = numstanzeperdisabili;
    }

    public Integer getNumbagni() {
        return numbagni;
    }

    public void setNumbagni(Integer numbagni) {
        this.numbagni = numbagni;
    }

    public Integer getAnnocostruzione() {
        return annocostruzione;
    }

    public void setAnnocostruzione(Integer annocostruzione) {
        this.annocostruzione = annocostruzione;
    }

    public Integer getAnnoristrutturazione() {
        return annoristrutturazione;
    }

    public void setAnnoristrutturazione(Integer annoristrutturazione) {
        this.annoristrutturazione = annoristrutturazione;
    }

    public String getAltreinfo() {
        return altreinfo;
    }

    public void setAltreinfo(String altreinfo) {
        this.altreinfo = altreinfo;
    }

    public Boolean getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(Boolean creditcard) {
        this.creditcard = creditcard;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPeriodobassastagione() {
        return periodobassastagione;
    }

    public void setPeriodobassastagione(String periodobassastagione) {
        this.periodobassastagione = periodobassastagione;
    }

    public String getPeriodoaltastagione() {
        return periodoaltastagione;
    }

    public void setPeriodoaltastagione(String periodoaltastagione) {
        this.periodoaltastagione = periodoaltastagione;
    }

}