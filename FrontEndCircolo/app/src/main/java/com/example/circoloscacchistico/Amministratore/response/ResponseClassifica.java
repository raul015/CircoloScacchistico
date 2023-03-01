package com.example.circoloscacchistico.Amministratore.response;

public class ResponseClassifica {

    private Integer idtorneo;
    private Integer iduser;
    private String firstname;
    private double punteggio;

    public ResponseClassifica(Integer idtorneo, Integer iduser, String firstname, double punteggio) {
        this.idtorneo = idtorneo;
        this.iduser = iduser;
        this.firstname = firstname;
        this.punteggio = punteggio;
    }

    public Integer getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(Integer idtorneo) {
        this.idtorneo = idtorneo;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public double getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(double punteggio) {
        this.punteggio = punteggio;
    }
}
