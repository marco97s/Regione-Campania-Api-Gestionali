package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "c59italiani")
public class C59Italiani {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('c59italiani_idc59italiani_seq'::regclass)")
    @Column(name = "idc59italiani", nullable = false)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comuneid")
    private Comuni comuneid;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "provinciaid", nullable = false)
    private Province provinciaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regioneid")
    private Regioni regioneid;

    @Column(name = "arrivati")
    private Integer arrivati;

    @Column(name = "partiti")
    private Integer partiti;

    @Column(name = "presenti")
    private Integer presenti;

    @Column(name = "camere")
    private Integer camere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strutturaid")
    private StruttureRicettive strutturaid;

    @Column(name = "datainserimento")
    private LocalDateTime datainserimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profiloinserimentoid")
    private ProfiliUtente profiloinserimentoid;


    public C59Italiani() {
    }

    public C59Italiani(Integer id, Integer anno, Integer mese, Integer giorno, Integer codept, Integer codapt, Comuni comuneid, Province provinciaid, Regioni regioneid, Integer arrivati, Integer partiti, Integer presenti, Integer camere, StruttureRicettive strutturaid, LocalDateTime datainserimento, ProfiliUtente profiloinserimentoid) {
        this.id = id;
        this.anno = anno;
        this.mese = mese;
        this.giorno = giorno;
        this.codept = codept;
        this.codapt = codapt;
        this.comuneid = comuneid;
        this.provinciaid = provinciaid;
        this.regioneid = regioneid;
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

    public Comuni getComuneid() {
        return comuneid;
    }

    public void setComuneid(Comuni comuneid) {
        this.comuneid = comuneid;
    }

    public Province getProvinciaid() {
        return provinciaid;
    }

    public String getProvinciaRegioneIstat() {
        if (provinciaid != null && provinciaid.getRegioneid() != null) {
            return provinciaid.getRegioneid().getCodiceistat();
        } else {
            return null;
        }
    }

    public void setProvinciaid(Province provinciaid) {
        this.provinciaid = provinciaid;
    }

    public Regioni getRegioneid() {
        return regioneid;
    }

    public void setRegioneid(Regioni regioneid) {
        this.regioneid = regioneid;
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

    @Override
    public String toString() {
        return "C59Italiani{" +
                "id=" + id +
                ", anno=" + anno +
                ", mese=" + mese +
                ", giorno=" + giorno +
                ", codept=" + codept +
                ", codapt=" + codapt +
                ", comuneid=" + comuneid +
                ", provinciaid=" + provinciaid +
                ", regioneid=" + regioneid +
                ", arrivati=" + arrivati +
                ", partiti=" + partiti +
                ", presenti=" + presenti +
                ", camere=" + camere +
                ", strutturaid=" + strutturaid +
                ", datainserimento=" + datainserimento +
                ", profiloinserimentoid=" + profiloinserimentoid +
                '}';
    }
}