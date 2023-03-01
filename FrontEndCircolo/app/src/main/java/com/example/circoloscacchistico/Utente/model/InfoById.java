package com.example.circoloscacchistico.Utente.model;

import java.util.ArrayList;
import java.util.List;

public class InfoById {

    private Integer id_torneo;
    private String nome_torneo;
    private String data_torneo;
    private String luogo;
    private Integer numero_turni;
    private Integer turno_attuale;
    private boolean in_corso;
    private ArrayList<Iscrizione> iscrizioni;
    private ArrayList<Partita> partite;

    public InfoById(Integer id_torneo, String nome_torneo, String data_torneo, String luogo, Integer numero_turni, Integer turno_attuale, boolean in_corso, ArrayList<Iscrizione> iscrizioni, ArrayList<Partita> partite) {
        this.id_torneo = id_torneo;
        this.nome_torneo = nome_torneo;
        this.data_torneo = data_torneo;
        this.luogo = luogo;
        this.numero_turni = numero_turni;
        this.turno_attuale = turno_attuale;
        this.in_corso = in_corso;;
        this.iscrizioni = iscrizioni;
        this.partite = partite;
    }

    public Integer getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(Integer id_torneo) {
        this.id_torneo = id_torneo;
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

    public boolean isIn_corso() {
        return in_corso;
    }

    public void setIn_corso(boolean in_corso) {
        this.in_corso = in_corso;
    }

    public List<Iscrizione> getIscrizioni() {
        return iscrizioni;
    }

    public void setIscrizioni(ArrayList<Iscrizione> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    public void setPartite(ArrayList<Partita> partite) {
        this.partite = partite;
    }
}
