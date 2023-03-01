package com.example.circoloscacchistico.Autenticazione;

public class Token {

    private String token;
    private String successo;

    public Token(String token , String successo){
        this.token = token;
        this.successo = successo;

    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token) {
        this.token = token;

    }

    public String getSuccesso() {
        return successo;
    }

    public void setSuccesso(String successo) {
        this.successo = successo;
    }
}
