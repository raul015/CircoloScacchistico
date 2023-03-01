package com.example.circoloscacchistico.Amministratore.response;

import java.net.Inet4Address;

public class ResponseGetPartita {

    private Integer id_partita;
    private Integer id_sfidante1;
    private Integer id_sfidante2;
    private Integer turno;
    private double risultato_sfidante1;
    private double risultato_sfidante2;

    public ResponseGetPartita(Integer id_partita,
                              Integer id_sfidante1,
                              Integer id_sfidante2,
                              Integer turno,
                              double risultato_sfidante1,
                              double risultato_sfidante2) {
        this.id_partita = id_partita;
        this.id_sfidante1 = id_sfidante1;
        this.id_sfidante2 = id_sfidante2;
        this.turno = turno;
        this.risultato_sfidante1 = risultato_sfidante1;
        this.risultato_sfidante2 = risultato_sfidante2;
    }

    public Integer getId_partita() {
        return id_partita;
    }

    public void setId_partita(Integer id_partita) {
        this.id_partita = id_partita;
    }

    public Integer getId_sfidante1() {
        return id_sfidante1;
    }

    public void setId_sfidante1(Integer id_sfidante1) {
        this.id_sfidante1 = id_sfidante1;
    }

    public Integer getId_sfidante2() {
        return id_sfidante2;
    }

    public void setId_sfidante2(Integer id_sfidante2) {
        this.id_sfidante2 = id_sfidante2;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Double getRisultato_sfidante1() {
        return risultato_sfidante1;
    }

    public void setRisultato_sfidante1(Double risultato_sfidante1) {
        this.risultato_sfidante1 = risultato_sfidante1;
    }

    public Double getRisultato_sfidante2() {
        return risultato_sfidante2;
    }

    public void setRisultato_sfidante2(Double risultato_sfidante2) {
        this.risultato_sfidante2 = risultato_sfidante2;
    }
}
