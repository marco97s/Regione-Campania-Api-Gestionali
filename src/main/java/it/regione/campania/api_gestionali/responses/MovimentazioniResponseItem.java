package it.regione.campania.api_gestionali.responses;

import java.util.List;

public class MovimentazioniResponseItem {
    String dataRilevazione;
    Integer camereOccupate;
    boolean strutturaChiusa;
    List<MovimentazioniResponseItemMovimentazione> movimentazioni;

    public MovimentazioniResponseItem() {
    }

    public MovimentazioniResponseItem(String dataRilevazione, Integer camereOccupate, List<MovimentazioniResponseItemMovimentazione> movimentazioni, boolean strutturaChiusa) {
        this.dataRilevazione = dataRilevazione;
        this.camereOccupate = camereOccupate;
        this.movimentazioni = movimentazioni;
        this.strutturaChiusa = strutturaChiusa;
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

    public boolean isStrutturaChiusa() {
        return strutturaChiusa;
    }

    public void setStrutturaChiusa(boolean strutturaChiusa) {
        this.strutturaChiusa = strutturaChiusa;
    }

    public List<MovimentazioniResponseItemMovimentazione> getMovimentazioni() {
        return movimentazioni;
    }

    public void setMovimentazioni(List<MovimentazioniResponseItemMovimentazione> movimentazioni) {
        this.movimentazioni = movimentazioni;
    }
}
