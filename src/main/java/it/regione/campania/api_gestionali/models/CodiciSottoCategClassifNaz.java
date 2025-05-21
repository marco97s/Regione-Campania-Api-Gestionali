package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;

@Entity
@Table(name = "codicisottocategclassifnaz")
public class CodiciSottoCategClassifNaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodicesccn")
    private Integer idcodicesccn;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "codicedenominazione")
    private String codicedenominazione;

    public CodiciSottoCategClassifNaz() {
    }

    public CodiciSottoCategClassifNaz(Integer idcodicesccn, String descrizione, String codicedenominazione) {
        this.idcodicesccn = idcodicesccn;
        this.descrizione = descrizione;
        this.codicedenominazione = codicedenominazione;
    }

    public Integer getIdcodicesccn() {
        return idcodicesccn;
    }

    public void setIdcodicesccn(Integer idcodicesccn) {
        this.idcodicesccn = idcodicesccn;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodicedenominazione() {
        return codicedenominazione;
    }

    public void setCodicedenominazione(String codicedenominazione) {
        this.codicedenominazione = codicedenominazione;
    }
}
