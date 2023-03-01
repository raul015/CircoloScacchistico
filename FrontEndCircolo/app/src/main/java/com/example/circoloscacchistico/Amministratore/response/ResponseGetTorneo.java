package com.example.circoloscacchistico.Amministratore.response;

public class ResponseGetTorneo {

    private Integer id_torneo;
    private String nome_torneo;
    private String data_torneo;
    private String luogo;
    private Integer numero_turni;
    private Integer turno_attuale;
    private boolean in_corso;

    public ResponseGetTorneo(Integer id,
                             String nome_torneo,
                             String data_torneo,
                             String luogo,
                             Integer numero_turni,
                             Integer turno_attuale,
                             boolean in_corso) {
        this.id_torneo = id;
        this.nome_torneo = nome_torneo;
        this.data_torneo = data_torneo;
        this.luogo = luogo;
        this.numero_turni = numero_turni;
        this.turno_attuale = turno_attuale;
        this.in_corso = in_corso;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }

    public String getNome_torneo() {
        return nome_torneo;
    }

    public void setNome_torneo(String nome_torneo) {
        this.nome_torneo = nome_torneo;
    }

    public String getData_torneo() {
        return data_torneo;
    }

    public void setData_torneo(String data_torneo) {
        this.data_torneo = data_torneo;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Integer getNumero_turni() {
        return numero_turni;
    }

    public void setNumero_turni(Integer numero_turni) {
        this.numero_turni = numero_turni;
    }

    public Integer getTurno_attuale() {
        return turno_attuale;
    }

    public void setTurno_attuale(Integer turno_attuale) {
        this.turno_attuale = turno_attuale;
    }

    public boolean isIn_corso() {return in_corso;}

    public void setIn_corso(boolean in_corso) {this.in_corso = in_corso;}
}
