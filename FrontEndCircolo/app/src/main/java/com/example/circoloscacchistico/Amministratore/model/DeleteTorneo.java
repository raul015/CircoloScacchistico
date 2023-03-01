package com.example.circoloscacchistico.Amministratore.model;

public class DeleteTorneo {

    private  Integer id_torneo;

    public DeleteTorneo(Integer id) {
        this.id_torneo = id;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }
}
