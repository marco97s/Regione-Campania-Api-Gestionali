package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "modc59")
public class Modc59 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('modc59_idmodc59_seq'::regclass)")
    @Column(name = "idmodc59", nullable = false)
    private Integer id;

    @Column(name = "anno")
    private Integer anno;

    @Column(name = "mese")
    private Integer mese;

    @Column(name = "giorno")
    private Integer giorno;

    @Column(name = "clientigiornoprec")
    private Integer clientigiornoprec;

    @Column(name = "totarrivati")
    private Integer totarrivati;

    @Column(name = "totpartiti")
    private Integer totpartiti;

    @Column(name = "totpresenti")
    private Integer totpresenti;

    @Column(name = "presentinotte")
    private Integer presentinotte;

    @Column(name = "totcamere")
    private Integer totcamere;

    @Column(name = "camerelibere")
    private Integer camerelibere;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "strutturaid", nullable = false)
    private StruttureRicettive strutturaid;

    @Size(max = 100)
    @Column(name = "filename", length = 100)
    private String filename;

    @Column(name = "datainserimento")
    private LocalDateTime datainserimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profiloinserimentoid")
    private ProfiliUtente profiloinserimentoid;

    @Column(name = "validato")
    private Boolean validato;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Integer getMese() {
        return mese;
    }

    public void setMese(Integer mese) {
        this.mese = mese;
    }

    public Integer getGiorno() {
        return giorno;
    }

    public void setGiorno(Integer giorno) {
        this.giorno = giorno;
    }

    public Integer getClientigiornoprec() {
        return clientigiornoprec;
    }

    public void setClientigiornoprec(Integer clientigiornoprec) {
        this.clientigiornoprec = clientigiornoprec;
    }

    public Integer getTotarrivati() {
        return totarrivati;
    }

    public void setTotarrivati(Integer totarrivati) {
        this.totarrivati = totarrivati;
    }

    public Integer getTotpartiti() {
        return totpartiti;
    }

    public void setTotpartiti(Integer totpartiti) {
        this.totpartiti = totpartiti;
    }

    public Integer getTotpresenti() {
        return totpresenti;
    }

    public void setTotpresenti(Integer totpresenti) {
        this.totpresenti = totpresenti;
    }

    public Integer getPresentinotte() {
        return presentinotte;
    }

    public void setPresentinotte(Integer presentinotte) {
        this.presentinotte = presentinotte;
    }

    public Integer getTotcamere() {
        return totcamere;
    }

    public void setTotcamere(Integer totcamere) {
        this.totcamere = totcamere;
    }

    public Integer getCamerelibere() {
        return camerelibere;
    }

    public void setCamerelibere(Integer camerelibere) {
        this.camerelibere = camerelibere;
    }

    public StruttureRicettive getStrutturaid() {
        return strutturaid;
    }

    public void setStrutturaid(StruttureRicettive strutturaid) {
        this.strutturaid = strutturaid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(LocalDateTime datainserimento) {
        this.datainserimento = datainserimento;
    }

    public ProfiliUtente getProfiloinserimentoid() {
        return profiloinserimentoid;
    }

    public void setProfiloinserimentoid(ProfiliUtente profiloinserimentoid) {
        this.profiloinserimentoid = profiloinserimentoid;
    }

    public Boolean getValidato() {
        return validato;
    }

    public void setValidato(Boolean validato) {
        this.validato = validato;
    }

    public Integer getNumLetti() {
        return strutturaid.getNumpostiletto();
    }

    public Integer getNumCamere() {
        return strutturaid.getNumcamere();
    }

    public Integer getCamereDisponibili(List<Modc59> listC59) {
        if (listC59 == null || this.getStrutturaid() == null || this.getStrutturaid().getCir() == null) {
            return 0; // Valore di default in caso di dati mancanti
        }
    
        long nC59 = listC59.stream()
                .filter(c59 -> c59.getStrutturaid() != null 
                            && c59.getStrutturaid().getCir() != null 
                            && c59.getStrutturaid().getCir().equals(this.getStrutturaid().getCir()))
                .count();
    
        Integer numCamere = this.getStrutturaid().getNumcamere(); // Evita chiamate ripetute
        return (numCamere != null) ? (int) (numCamere * nC59) : 0;
    }

    @Override
    public String toString() {
        return "Modc59{" +
                "id=" + id +
                ", anno=" + anno +
                ", mese=" + mese +
                ", giorno=" + giorno +
                ", clientigiornoprec=" + clientigiornoprec +
                ", totarrivati=" + totarrivati +
                ", totpartiti=" + totpartiti +
                ", totpresenti=" + totpresenti +
                ", presentinotte=" + presentinotte +
                ", totcamere=" + totcamere +
                ", camerelibere=" + camerelibere +
                ", strutturaid=" + strutturaid +
                ", filename='" + filename + '\'' +
                ", datainserimento=" + datainserimento +
                ", profiloinserimentoid=" + profiloinserimentoid +
                ", validato=" + validato +
                '}';
    }
}