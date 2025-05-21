package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;

@Entity
@Table(name = "codicicategorieclassifnaz")
public class CodiciCategorieClassifNaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodiceccn")
    private Integer idcodiceccn;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "codicedenominazione")
    private String codicedenominazione;

    public CodiciCategorieClassifNaz() {
    }

    public CodiciCategorieClassifNaz(Integer idcodiceccn, String descrizione) {
        this.idcodiceccn = idcodiceccn;
        this.descrizione = descrizione;
    }

    public Integer getIdcodiceccn() {
        return idcodiceccn;
    }

    public void setIdcodiceccn(Integer idcodiceccn) {
        this.idcodiceccn = idcodiceccn;
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
