package com.example.circoloscacchistico.Utente.response;



public class ResponseGetIscrizione {

    private Integer id_iscrizione;
    private float punteggio;

    public ResponseGetIscrizione(Integer id_iscrizione, float punteggio) {
        this.id_iscrizione = id_iscrizione;
        this.punteggio = punteggio;
    }

    public Integer getId_iscrizione() {
        return id_iscrizione;
    }

    public void setId_iscrizione(Integer id_iscrizione) {
        this.id_iscrizione = id_iscrizione;
    }

    public float getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(float punteggio) {
        this.punteggio = punteggio;
    }
}
