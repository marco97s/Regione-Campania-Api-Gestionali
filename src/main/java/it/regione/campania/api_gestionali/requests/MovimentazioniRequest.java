package it.regione.campania.api_gestionali.requests;

import java.util.List;

public class MovimentazioniRequest {
    private List<MovimentazioniRequestItem> giornate;

    public MovimentazioniRequest() {
    }

    public MovimentazioniRequest(List<MovimentazioniRequestItem> giornate) {
        this.giornate = giornate;
    }

    public List<MovimentazioniRequestItem> getGiornate() {
        return giornate;
    }

    public void setGiornate(List<MovimentazioniRequestItem> giornate) {
        this.giornate = giornate;
    }
}
