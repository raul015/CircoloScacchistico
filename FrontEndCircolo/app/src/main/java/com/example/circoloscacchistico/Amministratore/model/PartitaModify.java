package com.example.circoloscacchistico.Amministratore.model;

public class PartitaModify {

    private Integer id_partita;
    private double punti_bianco;
    private double punti_nero;

    public PartitaModify(Integer id_partita, double punti_bianco, double punti_nero) {

        this.id_partita = id_partita;
        this.punti_bianco = punti_bianco;
        this.punti_nero = punti_nero;
    }

    public Integer getId_partita() {
        return id_partita;
    }

    public void setId_partita(Integer id_partita) {
        this.id_partita = id_partita;
    }

    public double getPunti_bianco() {
        return punti_bianco;
    }

    public void setPunti_bianco(double punti_bianco) {
        this.punti_bianco = punti_bianco;
    }

    public double getPunti_nero() {
        return punti_nero;
    }

    public void setPunti_nero(double punti_nero) {
        this.punti_nero = punti_nero;
    }
}
