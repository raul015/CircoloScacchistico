package com.example.circoloscacchistico.Autenticazione.model;

public class DataLoginModel {

    // Classe che sarà il template per i dati a cui andremo a fare
    // operazione di parsing

    private String email;
    private String password;
    private String Token;

    public DataLoginModel(String email, String password){
        this.email = email;
        this.password = password;

    }


    // Getters

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
