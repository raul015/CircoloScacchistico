package com.example.circoloscacchistico.Amministratore.model;

public class ModifyLuogoTorneo {

    private Integer id_torneo;
    private String luogo;

    public ModifyLuogoTorneo(Integer id, String luogo) {
        this.id_torneo = id;
        this.luogo = luogo;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
}
