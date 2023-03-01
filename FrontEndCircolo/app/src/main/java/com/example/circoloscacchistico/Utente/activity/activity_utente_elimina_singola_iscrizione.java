package com.example.circoloscacchistico.Utente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.activity.AmministratoreDeleteTorneo;
import com.example.circoloscacchistico.Amministratore.activity.DeleteSingleTorneo;
import com.example.circoloscacchistico.Amministratore.model.DeleteTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Utente.UserIscrizioneAPICall;
import com.example.circoloscacchistico.Utente.model.RequestId;
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

public class activity_utente_elimina_singola_iscrizione extends AppCompatActivity {

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
    private  Integer turno_b;

    private Integer id_iscrizione_b;

    Context context;
    String token;
    RequestId delete; // ha come unico campo un ID...

    private UserIscrizioneAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Iscrizione/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utente_elimina_singola_iscrizione);

        id = (TextView)findViewById(R.id.iscrizionedeleteid);
        nome =(TextView)findViewById(R.id.iscrizionedeletenome);
        luogo = (TextView) findViewById(R.id.iscrizionedeleteluogo);
        data = (TextView) findViewById(R.id.iscrizionedeletedata);
        turni = (TextView) findViewById(R.id.iscrizionedeleteturni);
        turno = (TextView) findViewById(R.id.iscrizionedeleteturno);
        bottone = (Button) findViewById(R.id.Btniscrizionedelete);

        context = this;
        token =  Store.getToken(context);

        Intent intent = getIntent();
        id_b = intent.getExtras().getInt("id");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");
        turno_b = intent.getExtras().getInt("turno");

        id_iscrizione_b = intent.getExtras().getInt("id_iscrizione");

        id.setText(id_b.toString());
        nome.setText(nome_b);
        luogo.setText(luogo_b);
        data.setText(data_b);
        turni.setText(turni_b.toString());
        turno.setText(turno_b.toString());

        delete = new RequestId(id_iscrizione_b);


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

    public void deleteIscrizioneTorneo(View view){

        System.out.println("Bottone schiacciato...");

        // Cambio dopo eliminazione
        Call<ResponseIscrizione> call = apiInterface.deleteIscrizioneTorneo(delete);

        call.enqueue(new Callback<ResponseIscrizione>() {
            @Override
            public void onResponse(Call<ResponseIscrizione> call, Response<ResponseIscrizione> response) {
                Toast.makeText(activity_utente_elimina_singola_iscrizione.this,"Successo! ", Toast.LENGTH_SHORT).show();
                goToUtentDeleteTorneo();
                //goToAmministratoreDeleteTorneo();

            }

            @Override
            public void onFailure(Call<ResponseIscrizione> call, Throwable t) {
                Toast.makeText(activity_utente_elimina_singola_iscrizione.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void goToUtentDeleteTorneo(){
        Intent intent = new Intent(this, activity_utente_elimina_iscrizione_torneo.class);
        startActivity(intent);
    }


}