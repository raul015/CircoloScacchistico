package com.example.circoloscacchistico.Utente.model;

import java.util.function.DoubleBinaryOperator;

public class Iscrizione {

    private Integer id_iscrizione;
    private Double punteggio;


    public Iscrizione(Integer id_iscrizione, Double punteggio) {
        this.id_iscrizione = id_iscrizione;
        this.punteggio = punteggio;
    }

    public Integer getId_iscrizione() {
        return id_iscrizione;
    }

    public void setId_iscrizione(Integer id_iscrizione) {
        this.id_iscrizione = id_iscrizione;
    }

    public Double getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Double punteggio) {
        this.punteggio = punteggio;
    }
}
