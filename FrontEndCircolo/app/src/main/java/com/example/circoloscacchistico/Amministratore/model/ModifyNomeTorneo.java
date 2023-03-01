package com.example.circoloscacchistico.Amministratore.model;

public class ModifyNomeTorneo {


    private Integer id_torneo;
    private String nome_torneo;

    public ModifyNomeTorneo(Integer id, String nome_torneo) {
        this.id_torneo = id;
        this.nome_torneo = nome_torneo;
    }

    public String getNome_torneo() {
        return nome_torneo;
    }

    public Integer getId() {
        return id_torneo;
    }

    public void setId(Integer id) {
        this.id_torneo = id;
    }

    public void setNome_torneo(String nome_torneo) {
        this.nome_torneo = nome_torneo;
    }
}
