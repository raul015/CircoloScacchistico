package com.example.circoloscacchistico.Autenticazione.response;

public class ResponseNewPasswordModel {
    private  String successo ;

    public ResponseNewPasswordModel(String successo) {
        this.successo = successo;
    }

    public String getSuccesso() {
        return successo;
    }

    public void setSuccesso(String successo) {
        this.successo = successo;
    }
}
