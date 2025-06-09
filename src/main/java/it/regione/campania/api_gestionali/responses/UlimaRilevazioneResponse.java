package it.regione.campania.api_gestionali.responses;

public class UlimaRilevazioneResponse {
    private String dataUltimaRilevazione;

    public UlimaRilevazioneResponse() {
    }

    public UlimaRilevazioneResponse(String dataUltimaRilevazione) {
        this.dataUltimaRilevazione = dataUltimaRilevazione;
    }

    public String getDataUltimaRilevazione() {
        return dataUltimaRilevazione;
    }

    public void setDataUltimaRilevazione(String dataUltimaRilevazione) {
        this.dataUltimaRilevazione = dataUltimaRilevazione;
    }
}
