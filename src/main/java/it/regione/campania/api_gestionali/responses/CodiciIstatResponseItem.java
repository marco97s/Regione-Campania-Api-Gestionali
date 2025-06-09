package it.regione.campania.api_gestionali.responses;

public class CodiciIstatResponseItem {
    private String codiceIstat;
    private String descrizione;

    public CodiciIstatResponseItem() {
    }

    public CodiciIstatResponseItem(String codiceIstat, String descrizione) {
        this.codiceIstat = codiceIstat;
        this.descrizione = descrizione;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
