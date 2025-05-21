package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "strutturericettive")
public class StruttureRicettive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('strutturericettive_idstrutturaricettiva_seq'::regclass)")
    @Column(name = "idstrutturaricettiva", nullable = false)
    private Integer idstrutturaricettiva;

    @Size(max = 250)
    @Column(name = "denominazione", length = 250)
    private String denominazione;

    @Size(max = 250)
    @Column(name = "denominazioneen", length = 250)
    private String denominazioneen;

    @Size(max = 250)
    @Column(name = "email", length = 250)
    private String email;

    @Size(max = 250)
    @Column(name = "telefono", length = 250)
    private String telefono;

    @Size(max = 250)
    @Column(name = "pec", length = 250)
    private String pec;

    @Size(max = 50)
    @Column(name = "piva", length = 50)
    private String piva;

    @Size(max = 16)
    @Column(name = "cf", length = 16)
    private String cf;

    @Column(name = "numpiazzole")
    private Integer numpiazzole;

    @Column(name = "numpostiletto")
    private Integer numpostiletto;

    @Column(name = "numcamere")
    private Integer numcamere;

    @Column(name = "numbagni")
    private Integer numbagni;

    @Size(max = 50)
    @Column(name = "cir", length = 50)
    private String cir;

    @Size(max = 50)
    @Column(name = "cin", length = 50)
    private String cin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statocodiceidentnazid")
    private StatiCodiceIdentNazionale statocodiceidentnazid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codmacrocategclassifnazid")
    private CodiciMacroCategClassifNazionale codmacrocategclassifnazid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codcategclassifnazid")
    private CodiciCategorieClassifNaz codcategclassifnazid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codsottocategclassifnazid")
    private CodiciSottoCategClassifNaz codsottocategclassifnazid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regioneubicazioneid")
    private Regioni regioneubicazioneid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comuneubicazioneid")
    private Comuni comuneubicazioneid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provubicazioneid")
    private Province provubicazioneid;

    @Size(max = 10)
    @Column(name = "codiceistatregione", length = 10)
    private String codiceistatregione;

    @Size(max = 10)
    @Column(name = "codiceistatprov", length = 10)
    private String codiceistatprov;

    @Size(max = 10)
    @Column(name = "codiceistatcomune", length = 10)
    private String codiceistatcomune;

    @Size(max = 10)
    @Column(name = "capubicazione", length = 10)
    private String capubicazione;

    @Size(max = 250)
    @Column(name = "viaubicazione", length = 250)
    private String viaubicazione;

    @Size(max = 10)
    @Column(name = "civicoubicazione", length = 10)
    private String civicoubicazione;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statoattivitaid")
    private StatoAttivitaStruttura statoattivitaid;

    @Size(max = 10)
    @Column(name = "codiceatecoprevalente", length = 10)
    private String codiceatecoprevalente;

    @Size(max = 10)
    @Column(name = "codiceatecosecondario", length = 10)
    private String codiceatecosecondario;

    @Size(max = 50)
    @Column(name = "foglio", length = 50)
    private String foglio;

    @Size(max = 50)
    @Column(name = "particella", length = 50)
    private String particella;

    @Size(max = 10)
    @Column(name = "subalterno", length = 10)
    private String subalterno;

    @Column(name = "datainizioattivita")
    private LocalDate datainizioattivita;

    @Column(name = "datafineattivita")
    private LocalDate datafineattivita;

    @Column(name = "datacreazione")
    private Date datacreazione;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profilocreazioneid")
    private ProfiliUtente profilocreazioneid;

    @Column(name = "datamodifica")
    private Date datamodifica;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profilomodificaid")
    private ProfiliUtente profilomodificaid;

    @Size(max = 50)
    @Column(name = "latitudine", length = 50)
    private String latitudine;

    @Size(max = 50)
    @Column(name = "longitudine", length = 50)
    private String longitudine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoriaid")
    private CategoriestrutturaOld categoriaid;

    @Column(name = "stagionale")
    private Boolean stagionale;

    @Column(name = "dataaperturastagionale")
    private LocalDate dataaperturastagionale;

    @Column(name = "datachiusurastagionale")
    private LocalDate datachiusurastagionale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoriastrutturaid")
    private CategoriestrutturaOld categoriastrutturaid;

    @Size(max = 250)
    @Column(name = "indirizzochiusurastagionalevia", length = 250)
    private String indirizzochiusurastagionalevia;

    @Size(max = 250)
    @Column(name = "indirizzochiusurastagionalecap", length = 250)
    private String indirizzochiusurastagionalecap;

    @Size(max = 250)
    @Column(name = "indirizzochiusurastagionaletelefono", length = 250)
    private String indirizzochiusurastagionaletelefono;

    @Column(name = "indirizzochiusurastagionaleprovinciaid")
    private Integer indirizzochiusurastagionaleprovinciaid;

    @Column(name = "indirizzochiusurastagionalecomuneid")
    private Integer indirizzochiusurastagionalecomuneid;

    @Column(name = "caratteristichecameretipo")
    private Integer caratteristichecameretipo;

    @Column(name = "checkin")
    private String checkin;

    @Column(name = "checkout")
    private String checkout;

    @Column(name = "numunitaabitative")
    private Integer numunitaabitative;

    @Column(name = "tipologiasottocategclassifnazid")
    private Integer tipologiasottocategclassifnazid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "valutazioneid")
    private Valutazioneinfo valutazioneid;

    @Column(name = "numpostibarca")
    private Integer numpostibarca;

    @OneToMany(mappedBy = "strutturaricettivaid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Strutturericettiveindirizzi> indirizzi = new ArrayList<>();

    public List<Strutturericettiveindirizzi> getIndirizzi() {
        return indirizzi;
    }

    public Integer getIdstrutturaricettiva() {
        return idstrutturaricettiva;
    }

    public void setIdstrutturaricettiva(Integer idstrutturaricettiva) {
        this.idstrutturaricettiva = idstrutturaricettiva;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getDenominazioneen() {
        return denominazioneen;
    }

    public void setDenominazioneen(String denominazioneen) {
        this.denominazioneen = denominazioneen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public Integer getNumpiazzole() {
        return numpiazzole;
    }

    public void setNumpiazzole(Integer numpiazzole) {
        this.numpiazzole = numpiazzole;
    }

    public Integer getNumpostiletto() {
        return numpostiletto != null ? numpostiletto : 0;
    }

    public void setNumpostiletto(Integer numpostiletto) {
        this.numpostiletto = numpostiletto;
    }

    public Integer getNumcamere() {
        return numcamere != null ? numcamere : 0;
    }

    public void setNumcamere(Integer numcamere) {
        this.numcamere = numcamere;
    }

    public String getCir() {
        return cir;
    }

    public void setCir(String cir) {
        this.cir = cir;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public StatiCodiceIdentNazionale getStatocodiceidentnazid() {
        return statocodiceidentnazid;
    }

    public void setStatocodiceidentnazid(StatiCodiceIdentNazionale statocodiceidentnazid) {
        this.statocodiceidentnazid = statocodiceidentnazid;
    }

    public CodiciMacroCategClassifNazionale getCodmacrocategclassifnazid() {
        return codmacrocategclassifnazid;
    }

    public void setCodmacrocategclassifnazid(CodiciMacroCategClassifNazionale codmacrocategclassifnazid) {
        this.codmacrocategclassifnazid = codmacrocategclassifnazid;
    }

    public CodiciCategorieClassifNaz getCodcategclassifnazid() {
        return codcategclassifnazid;
    }

    public void setCodcategclassifnazid(CodiciCategorieClassifNaz codcategclassifnazid) {
        this.codcategclassifnazid = codcategclassifnazid;
    }

    public CodiciSottoCategClassifNaz getCodsottocategclassifnazid() {
        return codsottocategclassifnazid;
    }

    public void setCodsottocategclassifnazid(CodiciSottoCategClassifNaz codsottocategclassifnazid) {
        this.codsottocategclassifnazid = codsottocategclassifnazid;
    }

    public Regioni getRegioneubicazioneid() {
        return regioneubicazioneid;
    }

    public void setRegioneubicazioneid(Regioni regioneubicazioneid) {
        this.regioneubicazioneid = regioneubicazioneid;
    }

    public Comuni getComuneubicazioneid() {
        return comuneubicazioneid;
    }

    public void setComuneubicazioneid(Comuni comuneubicazioneid) {
        this.comuneubicazioneid = comuneubicazioneid;
    }

    public Province getProvubicazioneid() {
        return provubicazioneid;
    }

    public void setProvubicazioneid(Province provubicazioneid) {
        this.provubicazioneid = provubicazioneid;
    }

    public String getCodiceistatregione() {
        return codiceistatregione;
    }

    public void setCodiceistatregione(String codiceistatregione) {
        this.codiceistatregione = codiceistatregione;
    }

    public String getCodiceistatprov() {
        return codiceistatprov;
    }

    public void setCodiceistatprov(String codiceistatprov) {
        this.codiceistatprov = codiceistatprov;
    }

    public String getCodiceistatcomune() {
        return codiceistatcomune;
    }

    public void setCodiceistatcomune(String codiceistatcomune) {
        this.codiceistatcomune = codiceistatcomune;
    }

    public String getCapubicazione() {
        return capubicazione;
    }

    public void setCapubicazione(String capubicazione) {
        this.capubicazione = capubicazione;
    }

    public String getViaubicazione() {
        return viaubicazione;
    }

    public void setViaubicazione(String viaubicazione) {
        this.viaubicazione = viaubicazione;
    }

    public String getCivicoubicazione() {
        return civicoubicazione;
    }

    public void setCivicoubicazione(String civicoubicazione) {
        this.civicoubicazione = civicoubicazione;
    }

    public StatoAttivitaStruttura getStatoattivitaid() {
        return statoattivitaid;
    }

    public void setStatoattivitaid(StatoAttivitaStruttura statoattivitaid) {
        this.statoattivitaid = statoattivitaid;
    }

    public String getCodiceatecoprevalente() {
        return codiceatecoprevalente;
    }

    public void setCodiceatecoprevalente(String codiceatecoprevalente) {
        this.codiceatecoprevalente = codiceatecoprevalente;
    }

    public String getCodiceatecosecondario() {
        return codiceatecosecondario;
    }

    public void setCodiceatecosecondario(String codiceatecosecondario) {
        this.codiceatecosecondario = codiceatecosecondario;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getParticella() {
        return particella;
    }

    public void setParticella(String particella) {
        this.particella = particella;
    }

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public LocalDate getDatainizioattivita() {
        return datainizioattivita;
    }

    public void setDatainizioattivita(LocalDate datainizioattivita) {
        this.datainizioattivita = datainizioattivita;
    }

    public LocalDate getDatafineattivita() {
        return datafineattivita;
    }

    public void setDatafineattivita(LocalDate datafineattivita) {
        this.datafineattivita = datafineattivita;
    }

    public Date getDatacreazione() {
        return datacreazione;
    }

    public void setDatacreazione(Date datacreazione) {
        this.datacreazione = datacreazione;
    }

    public ProfiliUtente getProfilocreazioneid() {
        return profilocreazioneid;
    }

    public void setProfilocreazioneid(ProfiliUtente profilocreazioneid) {
        this.profilocreazioneid = profilocreazioneid;
    }

    public Date getDatamodifica() {
        return datamodifica;
    }

    public void setDatamodifica(Date datamodifica) {
        this.datamodifica = datamodifica;
    }

    public ProfiliUtente getProfilomodificaid() {
        return profilomodificaid;
    }

    public void setProfilomodificaid(ProfiliUtente profilomodificaid) {
        this.profilomodificaid = profilomodificaid;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public CategoriestrutturaOld getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(CategoriestrutturaOld categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Boolean getStagionale() {
        return stagionale;
    }

    public void setStagionale(Boolean stagionale) {
        this.stagionale = stagionale;
    }

    public LocalDate getDataaperturastagionale() {
        return dataaperturastagionale;
    }

    public void setDataaperturastagionale(LocalDate dataaperturastagionale) {
        this.dataaperturastagionale = dataaperturastagionale;
    }

    public LocalDate getDatachiusurastagionale() {
        return datachiusurastagionale;
    }

    public void setDatachiusurastagionale(LocalDate datachiusurastagionale) {
        this.datachiusurastagionale = datachiusurastagionale;
    }

    public CategoriestrutturaOld getCategoriastrutturaid() {
        return categoriastrutturaid;
    }

    public void setCategoriastrutturaid(CategoriestrutturaOld categoriastrutturaid) {
        this.categoriastrutturaid = categoriastrutturaid;
    }

    public String getIndirizzochiusurastagionalevia() {
        return indirizzochiusurastagionalevia;
    }

    public void setIndirizzochiusurastagionalevia(String indirizzochiusurastagionalevia) {
        this.indirizzochiusurastagionalevia = indirizzochiusurastagionalevia;
    }

    public String getIndirizzochiusurastagionalecap() {
        return indirizzochiusurastagionalecap;
    }

    public void setIndirizzochiusurastagionalecap(String indirizzochiusurastagionalecap) {
        this.indirizzochiusurastagionalecap = indirizzochiusurastagionalecap;
    }

    public String getIndirizzochiusurastagionaletelefono() {
        return indirizzochiusurastagionaletelefono;
    }

    public void setIndirizzochiusurastagionaletelefono(String indirizzochiusurastagionaletelefono) {
        this.indirizzochiusurastagionaletelefono = indirizzochiusurastagionaletelefono;
    }

    public Integer getIndirizzochiusurastagionaleprovinciaid() {
        return indirizzochiusurastagionaleprovinciaid;
    }

    public void setIndirizzochiusurastagionaleprovinciaid(Integer indirizzochiusurastagionaleprovinciaid) {
        this.indirizzochiusurastagionaleprovinciaid = indirizzochiusurastagionaleprovinciaid;
    }

    public Integer getIndirizzochiusurastagionalecomuneid() {
        return indirizzochiusurastagionalecomuneid;
    }

    public void setIndirizzochiusurastagionalecomuneid(Integer indirizzochiusurastagionalecomuneid) {
        this.indirizzochiusurastagionalecomuneid = indirizzochiusurastagionalecomuneid;
    }

    public Integer getCaratteristichecameretipo() {
        return caratteristichecameretipo;
    }

    public void setCaratteristichecameretipo(Integer caratteristichecameretipo) {
        this.caratteristichecameretipo = caratteristichecameretipo;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public Integer getNumunitaabitative() {
        return numunitaabitative;
    }

    public void setNumunitaabitative(Integer numunitaabitative) {
        this.numunitaabitative = numunitaabitative;
    }

    public Integer getTipologiasottocategclassifnazid() {
        return tipologiasottocategclassifnazid;
    }

    public void setTipologiasottocategclassifnazid(Integer tipologiasottocategclassifnazid) {
        this.tipologiasottocategclassifnazid = tipologiasottocategclassifnazid;
    }

    public Valutazioneinfo getValutazioneid() {
        return valutazioneid;
    }

    public void setValutazioneid(Valutazioneinfo valutazioneid) {
        this.valutazioneid = valutazioneid;
    }

    public Integer getNumpostibarca() {
        return numpostibarca;
    }

    public void setNumpostibarca(Integer numpostibarca) {
        this.numpostibarca = numpostibarca;
    }

    public Integer getNumBagni() {
        return numbagni;
    }

    public void setNumBagni(Integer numBagni) {
        this.numbagni = numBagni;
    }

    @Override
    public String toString() {
        return "StruttureRicettive [idstrutturaricettiva=" + idstrutturaricettiva + ", denominazione=" + denominazione
                + ", denominazioneen=" + denominazioneen + ", email=" + email + ", telefono=" + telefono + ", pec="
                + pec + ", piva=" + piva + ", cf=" + cf + ", numpiazzole=" + numpiazzole + ", numpostiletto="
                + numpostiletto + ", numcamere=" + numcamere + ", numbagni=" + numbagni + ", cir=" + cir + ", cin="
                + cin + ", statocodiceidentnazid=" + statocodiceidentnazid + ", codmacrocategclassifnazid="
                + codmacrocategclassifnazid + ", codcategclassifnazid=" + codcategclassifnazid
                + ", codsottocategclassifnazid=" + codsottocategclassifnazid + ", regioneubicazioneid="
                + regioneubicazioneid + ", comuneubicazioneid=" + comuneubicazioneid + ", provubicazioneid="
                + provubicazioneid + ", codiceistatregione=" + codiceistatregione + ", codiceistatprov="
                + codiceistatprov + ", codiceistatcomune=" + codiceistatcomune + ", capubicazione=" + capubicazione
                + ", viaubicazione=" + viaubicazione + ", civicoubicazione=" + civicoubicazione + ", statoattivitaid="
                + statoattivitaid + ", codiceatecoprevalente=" + codiceatecoprevalente + ", codiceatecosecondario="
                + codiceatecosecondario + ", foglio=" + foglio + ", particella=" + particella + ", subalterno="
                + subalterno + ", datainizioattivita=" + datainizioattivita + ", datafineattivita=" + datafineattivita
                + ", datacreazione=" + datacreazione + ", profilocreazioneid=" + profilocreazioneid + ", datamodifica="
                + datamodifica + ", profilomodificaid=" + profilomodificaid + ", latitudine=" + latitudine
                + ", longitudine=" + longitudine + ", categoriaid=" + categoriaid + ", stagionale=" + stagionale
                + ", dataaperturastagionale=" + dataaperturastagionale + ", datachiusurastagionale="
                + datachiusurastagionale + ", categoriastrutturaid=" + categoriastrutturaid
                + ", indirizzochiusurastagionalevia=" + indirizzochiusurastagionalevia
                + ", indirizzochiusurastagionalecap=" + indirizzochiusurastagionalecap
                + ", indirizzochiusurastagionaletelefono=" + indirizzochiusurastagionaletelefono
                + ", indirizzochiusurastagionaleprovinciaid=" + indirizzochiusurastagionaleprovinciaid
                + ", indirizzochiusurastagionalecomuneid=" + indirizzochiusurastagionalecomuneid
                + ", caratteristichecameretipo=" + caratteristichecameretipo + ", checkin=" + checkin + ", checkout="
                + checkout + ", numunitaabitative=" + numunitaabitative + ", tipologiasottocategclassifnazid="
                + tipologiasottocategclassifnazid + ", valutazioneid=" + valutazioneid + ", numpostibarca="
                + numpostibarca + ", indirizzi=" + indirizzi + "]";
    }

    
 }