package com.example.circoloscacchistico.Utente.model;

public class IscrizioneTorneo {

    private Torneo torneo;
    private User user;

    public IscrizioneTorneo(Torneo torneo, User user) {
        this.torneo = torneo;
        this.user = user;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public User getUser() {
        return user;
    }
}
