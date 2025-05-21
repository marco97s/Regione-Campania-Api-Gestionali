package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "c59stranieri")
public class C59Stranieri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('c59stranieri_idc59stranieri_seq'::regclass)")
    @Column(name = "idc59stranieri", nullable = false)
    private Integer id;

    @Column(name = "anno")
    private Integer anno;

    @Column(name = "mese")
    private Integer mese;

    @Column(name = "giorno")
    private Integer giorno;

    @Column(name = "codept")
    private Integer codept;

    @Column(name = "codapt")
    private Integer codapt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nazioneid", nullable = false)
    private Nazioni nazioneid;

    @Column(name = "arrivati")
    private Integer arrivati;

    @Column(name = "partiti")
    private Integer partiti;

    @Column(name = "presenti")
    private Integer presenti;

    @Column(name = "camere")
    private Integer camere;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "strutturaid", nullable = false)
    private StruttureRicettive strutturaid;

    @Column(name = "datainserimento")
    private LocalDate datainserimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profiloinserimentoid")
    private ProfiliUtente profiloinserimentoid;


    public C59Stranieri() {
    }


    public C59Stranieri(Integer id, Integer anno, Integer mese, Integer giorno, Integer codept, Integer codapt, Nazioni nazioneid, Integer arrivati, Integer partiti, Integer presenti, Integer camere, StruttureRicettive strutturaid, LocalDate datainserimento, ProfiliUtente profiloinserimentoid) {
        this.id = id;
        this.anno = anno;
        this.mese = mese;
        this.giorno = giorno;
        this.codept = codept;
        this.codapt = codapt;
        this.nazioneid = nazioneid;
        this.arrivati = arrivati;
        this.partiti = partiti;
        this.presenti = presenti;
        this.camere = camere;
        this.strutturaid = strutturaid;
        this.datainserimento = datainserimento;
        this.profiloinserimentoid = profiloinserimentoid;
    }

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

    public Integer getCodept() {
        return codept;
    }

    public void setCodept(Integer codept) {
        this.codept = codept;
    }

    public Integer getCodapt() {
        return codapt;
    }

    public void setCodapt(Integer codapt) {
        this.codapt = codapt;
    }

    public Nazioni getNazioneid() {
        return nazioneid;
    }

    public String getNazioneISO3() {
        if (nazioneid != null) {
            return nazioneid.getCodeiso3();
        } else {
            return null;
        }
    }

    public void setNazioneid(Nazioni nazioneid) {
        this.nazioneid = nazioneid;
    }

    public Integer getArrivati() {
        return arrivati;
    }

    public void setArrivati(Integer arrivati) {
        this.arrivati = arrivati;
    }

    public Integer getPartiti() {
        return partiti;
    }

    public void setPartiti(Integer partiti) {
        this.partiti = partiti;
    }

    public Integer getPresenti() {
        return presenti;
    }

    public void setPresenti(Integer presenti) {
        this.presenti = presenti;
    }

    public Integer getCamere() {
        return camere;
    }

    public void setCamere(Integer camere) {
        this.camere = camere;
    }

    public StruttureRicettive getStrutturaid() {
        return strutturaid;
    }

    public void setStrutturaid(StruttureRicettive strutturaid) {
        this.strutturaid = strutturaid;
    }

    public LocalDate getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(LocalDate datainserimento) {
        this.datainserimento = datainserimento;
    }

    public ProfiliUtente getProfiloinserimentoid() {
        return profiloinserimentoid;
    }

    public void setProfiloinserimentoid(ProfiliUtente profiloinserimentoid) {
        this.profiloinserimentoid = profiloinserimentoid;
    }

}