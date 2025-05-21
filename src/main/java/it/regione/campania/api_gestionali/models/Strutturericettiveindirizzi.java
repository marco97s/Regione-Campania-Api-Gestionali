package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "strutturericettiveindirizzi")
public class Strutturericettiveindirizzi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('strutturericettiveindirizzi_idstrutturaricettivaindirizzo_seq')")
    @Column(name = "idstrutturaricettivaindirizzo", nullable = false)
    private Integer idstrutturaricettivaindirizzo;

    @Size(max = 250)
    @Column(name = "indirizzoubicazione", length = 250)
    private String indirizzoubicazione;

    @Size(max = 10)
    @Column(name = "civicoubicazione", length = 10)
    private String civicoubicazione;

    @Size(max = 10)
    @Column(name = "capubicazione", length = 10)
    private String capubicazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strutturaricettivaid")
    private StruttureRicettive strutturaricettivaid;

    @Size(max = 10)
    @Column(name = "codicecatastale")
    private String codicecatastale;

    @Column(name = "sezione")
    private String sezione;

    @Column(name = "classe")
    private String classe;

    public Integer getIdstrutturaricettivaindirizzo() {
        return idstrutturaricettivaindirizzo;
    }

    public void setIdstrutturaricettivaindirizzo(Integer idstrutturaricettivaindirizzo) {
        this.idstrutturaricettivaindirizzo = idstrutturaricettivaindirizzo;
    }

    public String getIndirizzoubicazione() {
        return indirizzoubicazione;
    }

    public void setIndirizzoubicazione(String indirizzoubicazione) {
        this.indirizzoubicazione = indirizzoubicazione;
    }

    public String getCivicoubicazione() {
        return civicoubicazione;
    }

    public void setCivicoubicazione(String civicoubicazione) {
        this.civicoubicazione = civicoubicazione;
    }

    public String getCapubicazione() {
        return capubicazione;
    }

    public void setCapubicazione(String capubicazione) {
        this.capubicazione = capubicazione;
    }

    public StruttureRicettive getStrutturaricettivaid() {
        return strutturaricettivaid;
    }

    public void setStrutturaricettivaid(StruttureRicettive strutturaricettivaid) {
        this.strutturaricettivaid = strutturaricettivaid;
    }

    public @Size(max = 10) String getCodicecatastale() {
        return codicecatastale;
    }

    public void setCodicecatastale(@Size(max = 10) String codicecatastale) {
        this.codicecatastale = codicecatastale;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}