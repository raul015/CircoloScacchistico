package com.example.circoloscacchistico.Amministratore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.circoloscacchistico.Autenticazione.activity.LogActivity;
import com.example.circoloscacchistico.Autenticazione.activity.RegistrazioneActivity;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.Store;

public class MainAmministratoreActivity extends AppCompatActivity {

    CardView cardCreate;
    CardView cardDelete;
    CardView cardModify;
    CardView cardResult;
    CardView cardGenera;
    CardView cardLogout;

    TextView textViewAdministrator;

    Context context;
    String username;
    Integer id_utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_amministratore);

        cardCreate  = (CardView) findViewById(R.id.CardCreateAdministrator);
        cardDelete  = (CardView) findViewById(R.id.CardDeleteAdministrator);
        cardModify  = (CardView) findViewById(R.id.CardModifyAdministrator);
        cardResult  = (CardView) findViewById(R.id.CardARisultatoTurnidministrator);
        cardGenera  = (CardView) findViewById(R.id.CardGeneraTurniAdministrator);
        cardLogout  = (CardView) findViewById(R.id.CardLogoutAdministrator);

        textViewAdministrator = (TextView) findViewById(R.id.MydashAdministrator);

        context = this;
        username = Store.getUsername(context);
        id_utente = Integer.valueOf(Store.getId(context));

        textViewAdministrator.setText("Amministratore " + username);
        System.out.println("STO USANDO L'APP COME: " + username+ " ["+ id_utente +"]");


    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void goToCreateTorneo(View view) {
        Intent intent = new Intent(this, AmministratoreCreateTorneo.class);
        startActivity(intent);
    }

    public void goToDeleteTorneo(View view) {
        Intent intent = new Intent(this, AmministratoreDeleteTorneo.class);
        startActivity(intent);
    }

    public void goToModifyTorneo(View view) {
        Intent intent = new Intent(this, AmministratoreModificaTorneo.class);
        startActivity(intent);
    }

    public void goToGeneraTurno(View view){
        Intent intent = new Intent(this, AmministratoreGeneraTurno.class);
        startActivity(intent);
    }

    public void goToModifyPartite(View view){
        Intent intent = new Intent(this, ModificaPartita.class);
        startActivity(intent);

    }




}