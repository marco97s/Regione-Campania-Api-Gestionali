package it.regione.campania.api_gestionali.requests;

public class MovimentazioneRequestItemMovimentazione {
    private String codiceNazione;
    private String codiceProvincia;
    protected int arrivi;
    protected int presentiNottePrecedente;
    protected int partenze;

    MovimentazioneRequestItemMovimentazione(String codiceNazione, String codiceProvincia, int arrivi,
            int presentiNottePrecedente,
            int partenze) {
        this.codiceNazione = codiceNazione;
        this.codiceProvincia = codiceProvincia;
        this.arrivi = arrivi;
        this.presentiNottePrecedente = presentiNottePrecedente;
        this.partenze = partenze;
    }

    MovimentazioneRequestItemMovimentazione() {
    }

    public String getCodiceNazione() {
        return codiceNazione;
    }

    public void setCodiceNazione(String codiceNazione) {
        this.codiceNazione = codiceNazione;
    }

    public String getCodiceProvincia() {
        return codiceProvincia;
    }

    public void setCodiceProvincia(String codiceProvincia) {
        this.codiceProvincia = codiceProvincia;
    }

    public int getArrivi() {
        return arrivi;
    }

    public void setArrivi(int arrivi) {
        this.arrivi = arrivi;
    }

    public int getPresentiNottePrecedente() {
        return presentiNottePrecedente;
    }

    public void setPresentiNottePrecedente(int presentiNottePrecedente) {
        this.presentiNottePrecedente = presentiNottePrecedente;
    }

    public int getPartenze() {
        return partenze;
    }

    public void setPartenze(int partenze) {
        this.partenze = partenze;
    }
}
