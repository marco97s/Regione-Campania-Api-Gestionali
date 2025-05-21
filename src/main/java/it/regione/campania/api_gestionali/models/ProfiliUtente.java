package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "profiliutente")
public class ProfiliUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('profiliutente_idprofilo_seq'::regclass)")
    @Column(name = "idprofilo", nullable = false)
    private Integer idprofilo;

    @Size(max = 250)
    @NotNull
    @Column(name = "nome", nullable = false, length = 250)
    private String nome;

    @Size(max = 250)
    @NotNull
    @Column(name = "cognome", nullable = false, length = 250)
    private String cognome;

    @Size(max = 16)
    @NotNull
    @Column(name = "cf", nullable = false, length = 16)
    private String cf;

    @Size(max = 250)
    @NotNull
    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Size(max = 20)
    @Column(name = "piva", length = 20)
    private String piva;

    @Size(max = 250)
    @Column(name = "telefonofisso", length = 250)
    private String telefonofisso;

    @Size(max = 250)
    @Column(name = "telefonomob", length = 250)
    private String telefonomob;

    @Size(max = 250)
    @Column(name = "indirizzo", length = 250)
    private String indirizzo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nazioneid")
    private Nazioni nazioneid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provinciaid")
    private Province provinciaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuneid")
    private Comuni comuneid;

    @Column(name = "datainizio")
    private LocalDate datainizio;

    @Column(name = "datafine")
    private LocalDate datafine;

    @Column(name = "abilitato")
    private Boolean abilitato;

    @Size(max = 2000)
    @Column(name = "note", length = 2000)
    private String note;

    @Column(name = "canaccess")
    private Boolean canaccess;

    @Column(name = "canread")
    private Boolean canread;

    @Column(name = "candelete")
    private Boolean candelete;

    @Column(name = "canupdate")
    private Boolean canupdate;

    @Column(name = "ultimoaccesso")
    private Date ultimoaccesso;

    public Integer getIdprofilo() {
        return idprofilo;
    }

    public void setIdprofilo(Integer idprofilo) {
        this.idprofilo = idprofilo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getTelefonofisso() {
        return telefonofisso;
    }

    public void setTelefonofisso(String telefonofisso) {
        this.telefonofisso = telefonofisso;
    }

    public String getTelefonomob() {
        return telefonomob;
    }

    public void setTelefonomob(String telefonomob) {
        this.telefonomob = telefonomob;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Nazioni getNazioneid() {
        return nazioneid;
    }

    public void setNazioneid(Nazioni nazioneid) {
        this.nazioneid = nazioneid;
    }

    public Province getProvinciaid() {
        return provinciaid;
    }

    public void setProvinciaid(Province provinciaid) {
        this.provinciaid = provinciaid;
    }

    public Comuni getComuneid() {
        return comuneid;
    }

    public void setComuneid(Comuni comuneid) {
        this.comuneid = comuneid;
    }

    public LocalDate getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(LocalDate datainizio) {
        this.datainizio = datainizio;
    }

    public LocalDate getDatafine() {
        return datafine;
    }

    public void setDatafine(LocalDate datafine) {
        this.datafine = datafine;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCanaccess() {
        return canaccess;
    }

    public void setCanaccess(Boolean canaccess) {
        this.canaccess = canaccess;
    }

    public Boolean getCanread() {
        return canread;
    }

    public void setCanread(Boolean canread) {
        this.canread = canread;
    }

    public Boolean getCandelete() {
        return candelete;
    }

    public void setCandelete(Boolean candelete) {
        this.candelete = candelete;
    }

    public Boolean getCanupdate() {
        return canupdate;
    }

    public void setCanupdate(Boolean canupdate) {
        this.canupdate = canupdate;
    }

    public Date getUltimoaccesso() {
        return ultimoaccesso;
    }

    public void setUltimoaccesso(Date ultimoaccesso) {
        this.ultimoaccesso = ultimoaccesso;
    }

}