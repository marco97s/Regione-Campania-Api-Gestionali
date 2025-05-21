package it.regione.campania.api_gestionali.responses;

import java.util.List;

public class MovimentazioniResponse {
    private List<MovimentazioniResponseItem> giornate;
    private String errore;

    public MovimentazioniResponse() {
    }

    public MovimentazioniResponse(String error) {
        this.errore = error;
    }

    public MovimentazioniResponse(List<MovimentazioniResponseItem> giornate) {
        this.giornate = giornate;
    }

    public List<MovimentazioniResponseItem> getGiornate() {
        return giornate;
    }

    public void setGiornate(List<MovimentazioniResponseItem> giornate) {
        this.giornate = giornate;
    }

    public String getErrore() {
        return errore;
    }

    public void setErrore(String error) {
        this.errore = error;
    }
}
