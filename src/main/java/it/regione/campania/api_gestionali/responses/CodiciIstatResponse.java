package it.regione.campania.api_gestionali.responses;

import java.util.List;

public class CodiciIstatResponse {
    private List<CodiciIstatResponseItem> codiciIstatProvince;
    private List<CodiciIstatResponseItem> codiciIstatNazioni;

    public CodiciIstatResponse() {
    }

    public CodiciIstatResponse(List<CodiciIstatResponseItem> codiciIstatProvince, List<CodiciIstatResponseItem> codiciIstatNazioni) {
        this.codiciIstatProvince = codiciIstatProvince;
        this.codiciIstatNazioni = codiciIstatNazioni;
    }

    public List<CodiciIstatResponseItem> getCodiciIstatNazioni() {
        return codiciIstatNazioni;
    }

    public void setCodiciIstatNazioni(List<CodiciIstatResponseItem> codiciIstatNazioni) {
        this.codiciIstatNazioni = codiciIstatNazioni;
    }

    public List<CodiciIstatResponseItem> getCodiciIstatProvince() {
        return codiciIstatProvince;
    }

    public void setCodiciIstatProvince(List<CodiciIstatResponseItem> codiciIstatProvince) {
        this.codiciIstatProvince = codiciIstatProvince;
    }
}