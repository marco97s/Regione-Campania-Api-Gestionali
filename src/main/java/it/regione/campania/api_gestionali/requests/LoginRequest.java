package it.regione.campania.api_gestionali.requests;

public class LoginRequest {
    private String cusr;
    private String apiKey;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.cusr = username;
        this.apiKey = password;
    }

    public String getCusr() {
        return cusr;
    }

    public void setCusr(String username) {
        this.cusr = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String password) {
        this.apiKey = password;
    }
}
