package com.example.circoloscacchistico.Utente.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.circoloscacchistico.Amministratore.activity.AmministratoreCreateTorneo;
import com.example.circoloscacchistico.Autenticazione.activity.LogActivity;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.Store;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainUtenteActivity extends AppCompatActivity {

    Context context;
    CardView profilo;
    CardView iscrizione;
    CardView cancella;
    CardView classifica;
    CardView turni;
    CardView logout;
    TextView textViewUtente;
    String username;
    Integer id_utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_utente);
        context = this;

        profilo = (CardView) findViewById(R.id.CardProfileUtente);
        iscrizione = (CardView) findViewById(R.id.CardIscrizioneUtente);
        cancella = (CardView) findViewById(R.id.CardCancellaUtente);
        classifica = (CardView) findViewById(R.id.CardClassificaUtente);
        turni = (CardView) findViewById(R.id.CardTurniUtente);
        logout = (CardView)  findViewById(R.id.CardLogoutUtente);
        textViewUtente = (TextView) findViewById(R.id.MydashUtente);

        username = Store.getUsername(context);
        id_utente = Integer.valueOf(Store.getId(context));

        textViewUtente.setText("Utente " + Store.getUsername(context) + " ["+ id_utente +"]");
        System.out.println("STO USANDO L'APP COME: " + username);

    }


    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void goToIscrizioneTorneo(View view) {
        Intent intent = new Intent(this, activity_utente_iscrizione_torneo.class);
        startActivity(intent);
    }

    public void goToCancellaTorneo(View view) {
        Intent intent = new Intent(this, activity_utente_elimina_iscrizione_torneo.class);
        startActivity(intent);
    }

    public void goToClassificaTornei(View view){
        Intent intent = new Intent(this, ListaTorneiforClassifica.class);
        startActivity(intent);
    }

    public void goToTurniTornei(View view){
        Intent intent = new Intent(this, ListaTorneiforVisualTurni.class);
        startActivity(intent);
    }

}