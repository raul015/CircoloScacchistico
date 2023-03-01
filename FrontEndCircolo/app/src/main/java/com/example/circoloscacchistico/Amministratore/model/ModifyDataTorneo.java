package com.example.circoloscacchistico.Amministratore.model;

public class ModifyDataTorneo {

    private Integer id_torneo;
    private String data_torneo;

    public ModifyDataTorneo(Integer id, String data_torneo) {
        this.id_torneo = id;
        this.data_torneo = data_torneo;
    }

    public String getData_torneo() {
        return data_torneo;
    }

    public void setData_torneo(String data_torneo) {
        this.data_torneo = data_torneo;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }
}
