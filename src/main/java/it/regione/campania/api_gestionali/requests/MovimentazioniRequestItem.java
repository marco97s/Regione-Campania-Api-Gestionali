package it.regione.campania.api_gestionali.requests;

import java.util.List;

public class MovimentazioniRequestItem {
    String dataRilevazione;
    Integer camereOccupate;
    boolean strutturaChiusa = false;
    List<MovimentazioneRequestItemMovimentazione> movimentazioni;

    public MovimentazioniRequestItem() {
    }

    public MovimentazioniRequestItem(String dataRilevazione, Integer camereOccupate, List<MovimentazioneRequestItemMovimentazione> movimentazioni, boolean strutturaChiusa) {
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

    public List<MovimentazioneRequestItemMovimentazione> getMovimentazioni() {
        return movimentazioni;
    }

    public void setMovimentazioni(List<MovimentazioneRequestItemMovimentazione> movimentazioni) {
        this.movimentazioni = movimentazioni;
    }

    public void setStrutturaChiusa(boolean strutturaChiusa) {
        this.strutturaChiusa = strutturaChiusa;
    }

    public boolean isStrutturaChiusa() {
        return strutturaChiusa;
    }
}
