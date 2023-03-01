package com.example.circoloscacchistico.Utente.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Utente.UserIscrizioneAPICall;
import com.example.circoloscacchistico.Utente.model.IscrizioneTorneo;
import com.example.circoloscacchistico.Utente.model.Torneo;
import com.example.circoloscacchistico.Utente.model.User;
import com.example.circoloscacchistico.Utente.response.ResponseIscrizione;
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

public class activity_utente_iscrizione_singolo_torneo extends AppCompatActivity {


    TextView id;
    TextView nome;
    TextView luogo;
    TextView data;
    TextView turni;
    TextView turno;
    Button bottone;

    private  Integer id_b;
    private  String nome_b;
    private  String luogo_b;
    private String data_b;
    private  Integer turni_b;


    Context context;
    String token;
    Integer id_user;
    IscrizioneTorneo iscrizione;



    private UserIscrizioneAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Iscrizione/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_iscrizione_singolo_torneo);

        id = (TextView) findViewById(R.id.singleiscrizioneid);
        nome =(TextView)findViewById(R.id.singleiscrizionenome);
        luogo = (TextView) findViewById(R.id.singleiscrizioneluogo);
        data = (TextView) findViewById(R.id.singleiscrizionedata);
        turni = (TextView) findViewById(R.id.singleiscrizioneturni);
        bottone = (Button) findViewById(R.id.Btnsingleiscrizione);

        context = this;
        token =  Store.getToken(context);
        id_user = Integer.valueOf(Store.getId(context)); // Teoricamente mi da il valore intero..


        Intent intent = getIntent();
        id_b = intent.getExtras().getInt("id");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");


        id.setText(id_b.toString());
        nome.setText(nome_b);
        luogo.setText(luogo_b);
        data.setText(data_b);
        turni.setText(turni_b.toString());


        User user =  new User(id_user);
        System.out.println("id user : " + id_user);
        System.out.println("id torneo : " + id_b);
        Torneo torneo = new Torneo(id_b);
        iscrizione = new IscrizioneTorneo(torneo,user);

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

        apiInterface = retrofit.create(UserIscrizioneAPICall.class);
    }


    public void iscrizioneSingleTorneo(View view){

        System.out.println("Bottone schiacciato...");

        // Cambio dopo eliminazione
        Call<ResponseIscrizione> call = apiInterface.createPostIscrizione(iscrizione);

        call.enqueue(new Callback<ResponseIscrizione>() {
            @Override
            public void onResponse(Call<ResponseIscrizione> call, Response<ResponseIscrizione> response) {
                Toast.makeText(activity_utente_iscrizione_singolo_torneo.this,"Successo! " + response.body().getRisposta(), Toast.LENGTH_SHORT).show();
                goToUserIscrizioneTorneo();

            }

            @Override
            public void onFailure(Call<ResponseIscrizione> call, Throwable t) {
                Toast.makeText(activity_utente_iscrizione_singolo_torneo.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void goToUserIscrizioneTorneo(){
        Intent intent = new Intent(this, activity_utente_iscrizione_torneo.class);
        startActivity(intent);
    }




}