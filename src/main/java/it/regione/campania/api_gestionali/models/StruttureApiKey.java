package it.regione.campania.api_gestionali.models;

import jakarta.persistence.*;

@Entity
@Table(name = "struttureapikey")
public class StruttureApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cusr", nullable = false, length = 50)
    private String cusr;

    @Column(name = "apikey", nullable = false, length = 100)
    private String apikey;

    // Costruttori
    public StruttureApiKey() {
    }

    public StruttureApiKey(String cusr, String apikey) {
        this.cusr = cusr;
        this.apikey = apikey;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCusr() {
        return cusr;
    }

    public void setCusr(String cusr) {
        this.cusr = cusr;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    // toString (opzionale)
    @Override
    public String toString() {
        return "StruttureApiKey{" +
                "id=" + id +
                ", cusr='" + cusr + '\'' +
                ", apikey='" + apikey + '\'' +
                '}';
    }
}