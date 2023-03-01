package com.example.circoloscacchistico.Amministratore.model;

public class ModifyNturniTorneo {

    private  Integer id_torneo;
    private Integer numero_turni;

    public ModifyNturniTorneo(Integer id, Integer numero_turni) {
        this.id_torneo = id;
        this.numero_turni = numero_turni;
    }

    public Integer getNumero_turni() {
        return numero_turni;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }

    public void setNumero_turni(Integer numero_turni) {
        this.numero_turni = numero_turni;
    }
}
