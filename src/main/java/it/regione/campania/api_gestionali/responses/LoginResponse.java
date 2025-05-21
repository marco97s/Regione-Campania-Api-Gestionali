package it.regione.campania.api_gestionali.responses;

public class LoginResponse {
    private String token;
    private Long expiresIn;
    private String errore;

    public LoginResponse() {
    }

    public LoginResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public LoginResponse(String error) {
        this.errore = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getErrore() {
        return errore;
    }

    public void setErrore(String error) {
        this.errore = error;
    }
}
