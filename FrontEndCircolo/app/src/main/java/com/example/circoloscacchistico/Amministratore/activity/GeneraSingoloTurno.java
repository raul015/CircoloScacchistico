package com.example.circoloscacchistico.Amministratore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.model.ModifyStatoTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyTurnoTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.Store;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GeneraSingoloTurno extends AppCompatActivity {

    EditText turno_attuale;
    ImageView nuovo;
    ImageView termina_turno;
    TextView stato_torneo;

    TextView avviso;

    Context context;
    String token;

    private  Integer id_torneo;
    private  Integer turno_attuale_b;
    private  boolean stato_torneo_b;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";

    private ModifyStatoTorneo modifyStatoTorneo;
    private ModifyTurnoTorneo modifyTurnoTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_singolo_turno);

        turno_attuale = (EditText) findViewById(R.id.generaTurno_turnoAttuale);
        nuovo = (ImageView) findViewById(R.id.NuovoTurnoBtn);
        stato_torneo = (TextView) findViewById(R.id.statoTorneoGeneraTurno);
        avviso = (TextView) findViewById(R.id.AvvisoStato);
        avviso.setText(""); // Non voglio che si veda niente inzialmente...
        termina_turno = (ImageView) findViewById(R.id.TerminaTurnoBtn);
        // Permessi eliminazione

        context = this;
        token =  Store.getToken(context);

        Intent intent = getIntent();
        id_torneo= intent.getExtras().getInt("id");
        turno_attuale_b = intent.getExtras().getInt("turno");
        stato_torneo_b = intent.getExtras().getBoolean("stato");

        turno_attuale.setText(turno_attuale_b.toString());


        if(stato_torneo_b == true){

            stato_torneo.setText("torneo in corso");
            stato_torneo.setBackgroundColor(Color.parseColor("#00FF00"));
            avviso.setText("");
        }
        else{

            if(turno_attuale_b == 0){
                avviso.setText("Fai click su Avvia per avviare il torneo");
                stato_torneo.setText("Avvia");
            }
            else{
                    avviso.setText(""); // Stringa vuota...
                    stato_torneo.setText("torneo non in corso");
                    stato_torneo.setBackgroundColor(Color.parseColor("#FF0000"));
            }

        }

        // Retrofit
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(AdministratorAPICall.class);

    }


    public void cambiaStato(View view){

        // Pulsante stato.

        if(stato_torneo_b == false){

            if(turno_attuale_b == 0){
                // Lo stato verrà cambiato a true
                modifyStatoTorneo = new ModifyStatoTorneo(id_torneo,true);
                modifyStatoPutTorneo();
                turno_attuale_b  = 1;
            }
            else
                System.out.println("Non succede niente");
        }
        else {
                if(turno_attuale_b ==5){
                    modifyStatoTorneo = new ModifyStatoTorneo(id_torneo,false);
                    modifyStatoPutTorneo();
                }
        }
    }

    // Richiamo delle funzioni di callBack delle API

    public void modifyStatoPutTorneo(){

        Call<ResponseTorneo> call = apiInterface.modifyStatoPutTorneo(modifyStatoTorneo);
        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(GeneraSingoloTurno.this,"Successo! ", Toast.LENGTH_SHORT).show();
                if(modifyStatoTorneo.isIn_corso()){
                    stato_torneo.setText("torneo in corso");
                    stato_torneo.setBackgroundColor(Color.parseColor("#00FF00"));
                    goToListaIscrizioni();

                }
                else{
                    stato_torneo.setText("torneo non in corso");
                    stato_torneo.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                Toast.makeText(GeneraSingoloTurno.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(GeneraSingoloTurno.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void GeneraNuovoTurno(View view){

        // Pulsante freccia

        modifyTurnoTorneo = new ModifyTurnoTorneo(id_torneo,Integer.valueOf(turno_attuale.getText().toString()));

        GeneraTurno();
    }

    public void GeneraTurno(){

        Call<ResponseTorneo> call = apiInterface.GeneraTurno(modifyTurnoTorneo);
        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(GeneraSingoloTurno.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();
                goToListaIscrizioni();
            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(GeneraSingoloTurno.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToListaIscrizioni(){
        Intent intent = new Intent(this, AmministratoreGeneraTurno.class);
        startActivity(intent);
    }


    public void TerminaTurnoAttuale(View view){

        Integer nuovo = Integer.valueOf(turno_attuale.getText().toString())+1;
        modifyTurnoTorneo = new ModifyTurnoTorneo(id_torneo,nuovo);

        Call<ResponseTorneo> call = apiInterface.modifyTurnoPutTorneo(modifyTurnoTorneo);
        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(GeneraSingoloTurno.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();
                goToListaIscrizioni();
            }
            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(GeneraSingoloTurno.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void visualizzaClassifica(View view){

        Intent intent = new Intent(this, VisualizzaClassifica.class);
        intent.putExtra("id_torneo",id_torneo);
        intent.putExtra("turno_attuale_torneo",turno_attuale_b);
        startActivity(intent);

        // Devo passare     "id_torneo" : "1"  "turno_attuale" : "1" per la richiesta di classifica...
    }

}