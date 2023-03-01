package com.example.circoloscacchistico.Amministratore.model;

public class ModifyTurnoTorneo {

    private  Integer id_torneo;
    private Integer turno_attuale;

    public ModifyTurnoTorneo(Integer id, Integer turno_attuale) {
        this.id_torneo = id;
        this.turno_attuale = turno_attuale;
    }

    public Integer getTurno_attuale() {
        return turno_attuale;
    }

    public void setTurno_attuale(Integer turno_attuale) {
        this.turno_attuale = turno_attuale;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }

}
