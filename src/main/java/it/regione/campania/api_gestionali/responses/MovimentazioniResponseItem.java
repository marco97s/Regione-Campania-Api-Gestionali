package it.regione.campania.api_gestionali.responses;

import java.util.List;

public class MovimentazioniResponseItem {
    String dataRilevazione;
    Integer camereOccupate;
    List<MovimentazioniResponseItemMovimentazione> movimentazioni;

    public MovimentazioniResponseItem() {
    }

    public MovimentazioniResponseItem(String dataRilevazione, Integer camereOccupate, List<MovimentazioniResponseItemMovimentazione> movimentazioni) {
        this.dataRilevazione = dataRilevazione;
        this.camereOccupate = camereOccupate;
        this.movimentazioni = movimentazioni;
    }

    public String getDataRilevazione() {
        return dataRilevazione;
    }

    public void setDataRilevazione(String dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
    }

    public Integer getCamereOccupate() {
        return camereOccupate;
    }

    public void setCamereOccupate(Integer camereOccupate) {
        this.camereOccupate = camereOccupate;
    }

    public List<MovimentazioniResponseItemMovimentazione> getMovimentazioni() {
        return movimentazioni;
    }

    public void setMovimentazioni(List<MovimentazioniResponseItemMovimentazione> movimentazioni) {
        this.movimentazioni = movimentazioni;
    }
}
