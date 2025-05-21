package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "categoriestruttura_old")
public class CategoriestrutturaOld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('categoriestruttura_idcategoriastruttura_seq'::regclass)")
    @Column(name = "idcategoriastruttura", nullable = false)
    private Integer idcategoriastruttura;

    @Size(max = 100)
    @NotNull
    @Column(name = "descrizione", nullable = false, length = 100)
    private String descrizione;

    @Column(name = "categoria")
    private Integer categoria;

    @Size(max = 100)
    @Column(name = "tipologiaistat", length = 100)
    private String tipologiaistat;

    @Size(max = 100)
    @Column(name = "addregione", length = 100)
    private String addregione;

    @Size(max = 100)
    @Column(name = "addistat", length = 100)
    private String addistat;

    @Size(max = 100)
    @Column(name = "aggateco", length = 100)
    private String aggateco;

    public Integer getIdcategoriastruttura() {
        return idcategoriastruttura;
    }

    public void setIdcategoriastruttura(Integer idcategoriastruttura) {
        this.idcategoriastruttura = idcategoriastruttura;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getTipologiaistat() {
        return tipologiaistat;
    }

    public void setTipologiaistat(String tipologiaistat) {
        this.tipologiaistat = tipologiaistat;
    }

    public String getAddregione() {
        return addregione;
    }

    public void setAddregione(String addregione) {
        this.addregione = addregione;
    }

    public String getAddistat() {
        return addistat;
    }

    public void setAddistat(String addistat) {
        this.addistat = addistat;
    }

    public String getAggateco() {
        return aggateco;
    }

    public void setAggateco(String aggateco) {
        this.aggateco = aggateco;
    }


}