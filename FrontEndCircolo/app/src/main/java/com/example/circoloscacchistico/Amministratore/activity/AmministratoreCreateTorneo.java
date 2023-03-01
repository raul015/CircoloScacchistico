package com.example.circoloscacchistico.Amministratore.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.circoloscacchistico.Amministratore.model.CreateTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;

import com.example.circoloscacchistico.Autenticazione.activity.LogActivity;
import com.example.circoloscacchistico.R;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Util.Store;
import com.example.circoloscacchistico.databinding.ActivityMainBinding;

public class AmministratoreCreateTorneo extends AppCompatActivity {


    EditText editTextNomeTorneo;
    EditText editTextDataTorneo;
    EditText editTextLuogoTorneo;
    EditText editTextNumeroTurni;

    Context context;

    Button BtnConfermaTorneo;

    String token;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amministratore_crea_torneo);

        context = this;
        token =  Store.getToken(context);

        editTextNomeTorneo = (EditText) findViewById(R.id.TextNuovoTorneo) ;
        editTextDataTorneo = (EditText) findViewById(R.id.TextDataTorneo) ;
        editTextLuogoTorneo = (EditText) findViewById(R.id.TextLuogoTorneo);
        editTextNumeroTurni = (EditText) findViewById(R.id.TextNumeroTurni);
        BtnConfermaTorneo = (Button) findViewById(R.id.BtnCreaTorneo);

        // Devo chiamare l'API, ma per farlo mi serve inserire il Token che mi è stato dato
        //   durante l' operazion

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


    public void BtnClickCrea(View view){
        String nomeTorneo = editTextNomeTorneo.getText().toString();
        // Qui devo fare un parser per creare un oggetto LocalDateTime
        String dataTorneo = editTextDataTorneo.getText().toString();
        String luogoTorneo = editTextLuogoTorneo.getText().toString();


        Integer numeroTurni = Integer.parseInt(editTextNumeroTurni.getText().toString());

        // TO DO something

        if(controlloDati(nomeTorneo,dataTorneo,luogoTorneo,numeroTurni)){

            BtnConfermaTorneo.setBackgroundColor(Color.parseColor("#00FF00"));
            BtnConfermaTorneo.setText("Il torneo è stato creato");

            CreateTorneo torneo = new CreateTorneo(nomeTorneo,dataTorneo,luogoTorneo,numeroTurni);
            createPostNuovoTorneo(torneo);

            System.out.println("Non è stato possibile creare l'ggetto LocalDateTime");


        }
        else {

            BtnConfermaTorneo.setBackgroundColor(Color.parseColor("#FF0000"));
            BtnConfermaTorneo.setText("Il torneo non è stato create :(");
        }
    }



    // Controllo che tutti i campi siano stati rimepiti
    boolean controlloDati(String nomeTorneo, String dataTorneo, String luogoTorneo, Integer numeroTurni) {

        if(!nomeTorneo.isEmpty()) {

            if (!dataTorneo.isEmpty()) {

                if(!luogoTorneo.isEmpty())
                {
                    if(numeroTurni != null){

                        return true;
                    }
                }

            }
        }
        return false;
    }


    public void createPostNuovoTorneo(CreateTorneo torneo){

        Call<ResponseTorneo> call = apiInterface.createPostNuovoTorneo(torneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(AmministratoreCreateTorneo.this,"Successo! " + response.body().getRisposta() , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(AmministratoreCreateTorneo.this,"Fallimento", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void goToMainAdministrator(View view){

        Intent intent = new Intent(this, MainAmministratoreActivity.class);
        startActivity(intent);
    }

}
