package com.example.circoloscacchistico.Amministratore.model;

public class ModifyStatoTorneo {

    private Integer id_torneo;
    private boolean in_corso;


    public ModifyStatoTorneo(Integer id_torneo, boolean in_corso) {
        this.id_torneo = id_torneo;
        this.in_corso = in_corso;
    }

    public Integer getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(Integer id_torneo) {
        this.id_torneo = id_torneo;
    }

    public boolean isIn_corso() {
        return in_corso;
    }

    public void setIn_corso(boolean in_corso) {
        this.in_corso = in_corso;
    }
}
