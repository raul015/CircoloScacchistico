package com.example.circoloscacchistico.Amministratore.activity;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.AdministratorPartitaAPICall;
import com.example.circoloscacchistico.Amministratore.model.PartitaModify;
import com.example.circoloscacchistico.Amministratore.response.ResponsePartita;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.Store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModificaPartitaSingolo extends AppCompatActivity {

    TextView id_partita;
    TextView id_bianco;
    TextView id_nero;
    TextView turno;
    EditText ris_bianco;
    EditText ris_nero;
    Button bottone;

    Context context;
    String token;

    Integer id_partita_b;
    Integer id_bianco_b;
    Integer id_nero_b;
    Integer turno_b;
    double ris_bianco_b;
    double ris_nero_b;

    private  Integer id_b;
    private  String nome_b;
    private  String luogo_b;
    private String data_b;
    private  Integer turni_b;

    private AdministratorPartitaAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Partita/";

    PartitaModify partitaModify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_partita_singolo);

        id_partita = (TextView) findViewById(R.id.idPartitaModifica);
        id_bianco = (TextView) findViewById(R.id.idSfidante1PartitaModificata);
        id_nero = (TextView)  findViewById(R.id.idSfidante2PartitaModificata);
        turno = (TextView) findViewById(R.id.turnoPartitaModificata);
        ris_bianco = (EditText) findViewById(R.id.RisSfi1PartitaModificata);
        ris_nero = (EditText) findViewById(R.id.RisSfi2PartitaModificata);
        bottone = (Button) findViewById(R.id.BtnPartitaModificata);

        context = this;
        token =  Store.getToken(context);

        Intent intent = getIntent();

        id_partita_b= intent.getExtras().getInt("id_partita");
        id_bianco_b = intent.getExtras().getInt("id_bianco");
        id_nero_b = intent.getExtras().getInt("id_nero");
        turno_b = intent.getExtras().getInt("turno");
        ris_bianco_b = intent.getExtras().getDouble("ris_bianco");
        ris_nero_b = intent.getExtras().getDouble("ris_nero");

        id_partita.setText(id_partita_b.toString());
        id_bianco.setText(id_bianco_b.toString());
        id_nero.setText(id_nero_b.toString());
        turno.setText(turno_b.toString());
        ris_bianco.setText(Double.toString(ris_bianco_b));
        ris_nero.setText(Double.toString(ris_nero_b));


        id_b = intent.getExtras().getInt("id");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");

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

        apiInterface = retrofit.create(AdministratorPartitaAPICall.class);


    }

    public void modificaRisultatoPartita(View view){

        partitaModify = new PartitaModify(id_partita_b,Double.parseDouble(ris_bianco.getText().toString()),Double.parseDouble(ris_nero.getText().toString()));

        // Cambio dopo eliminazione
        Call<ResponsePartita> call = apiInterface.createPutModifyPartita(partitaModify);

        call.enqueue(new Callback<ResponsePartita>() {
            @Override
            public void onResponse(Call<ResponsePartita> call, Response<ResponsePartita> response) {
                Toast.makeText(ModificaPartitaSingolo.this,"Successo! ", Toast.LENGTH_SHORT).show();
                goToListaPartite();

            }

            @Override
            public void onFailure(Call<ResponsePartita> call, Throwable t) {
                Toast.makeText(ModificaPartitaSingolo.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void goToListaPartite(){
        Intent intent = new Intent(this, ModificaPartitaLista.class);
        intent.putExtra("id",id_b);
        intent.putExtra("nome",nome_b);
        intent.putExtra("luogo",luogo_b);
        intent.putExtra("data",data_b);
        intent.putExtra("turni",turni_b);

        startActivity(intent);
    }


}