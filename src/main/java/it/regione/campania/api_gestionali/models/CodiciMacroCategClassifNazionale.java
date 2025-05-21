package it.regione.campania.api_gestionali.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "codicimacrocategclassifnazionale")
public class CodiciMacroCategClassifNazionale {

    @Id
    @Column(name = "idcodicemccn")
    private Integer idcodicemccn;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "codicedenominazione")
    private String codicedenominazione;

    public CodiciMacroCategClassifNazionale() {
    }

    public CodiciMacroCategClassifNazionale(Integer idcodicemccn, String descrizione, String codicedenominazione) {
        this.idcodicemccn = idcodicemccn;
        this.descrizione = descrizione;
        this.codicedenominazione = codicedenominazione;
    }

    public Integer getIdcodicemccn() {
        return idcodicemccn;
    }

    public void setIdcodicemccn(Integer idcodicemccn) {
        this.idcodicemccn = idcodicemccn;
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
