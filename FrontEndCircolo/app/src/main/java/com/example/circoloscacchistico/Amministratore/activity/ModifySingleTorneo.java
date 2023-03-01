package com.example.circoloscacchistico.Amministratore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.model.ModifyDataTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyLuogoTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyNomeTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyNturniTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyTurnoTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.Autenticazione.model.DataRegisterModel;
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

public class ModifySingleTorneo extends AppCompatActivity {

    private  Integer id_iniziale;
    private  String nome_iniziale;
    private  String luogo_iniziale;
    private  String data_iniziale;
    private  Integer turni_iniziale;
    private  Integer turno_iniziale;


    private  String nome_finale;
    private  String luogo_finale;
    private  String data_finale;
    private  Integer turni_finale;
    private  Integer turno_finale;


    boolean nome_b;
    boolean luogo_b;
    boolean data_b;
    boolean turni_b;
    boolean turno_b;


    private ModifyNomeTorneo modifyNomeTorneo;
    private ModifyDataTorneo modifyDataTorneo;
    private ModifyLuogoTorneo modifyLuogoTorneo;
    private ModifyNturniTorneo modifyNturniTorneo;
    private ModifyTurnoTorneo modifyTurnoTorneo;

    Context context;
    String token;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";


    TextView id;
    EditText nome;
    EditText luogo;
    EditText data;
    EditText turni;
    EditText turno;

    Button bottone;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_single_torneo);

        context = this;
        token =  Store.getToken(context);


        id = (TextView) findViewById(R.id.singlemodifyid);
        nome = (EditText) findViewById(R.id.singlemodifynome);
        luogo = (EditText) findViewById(R.id.singlemodifyluogo);
        data = (EditText) findViewById(R.id.singlemodifydata);
        turni = (EditText)findViewById(R.id.singlemodifyturni);
        turno = (EditText)findViewById(R.id.singlemodifyturno);
        bottone = (Button) findViewById(R.id.Btnsinglemodify);

        Intent intent = getIntent();

        // Questi li confronto con i nuovi valori
        id_iniziale = intent.getExtras().getInt("id");
        nome_iniziale = intent.getExtras().getString("nome");
        luogo_iniziale = intent.getExtras().getString("luogo");
        data_iniziale = intent.getExtras().getString("data");
        turni_iniziale = intent.getExtras().getInt("turni");
        turno_iniziale = intent.getExtras().getInt("turno");

        id.setText(id_iniziale.toString());
        if(nome_iniziale != null)
            nome.setText(nome_iniziale);
        else
            nome.setText(" ");
        luogo.setText(luogo_iniziale);
        if(data_iniziale !=null)
            data.setText(data_iniziale);
        else
            data.setText(" ");
        if(turni_iniziale != null)
            turni.setText(turni_iniziale.toString());
        else
            turni.setText(" ");
        if(turno_iniziale != null)
            turno.setText(turno_iniziale.toString());
        else
            turno.setText(" ");

        nome_b = false;
        luogo_b = false;
        data_b = false;
        turni_b = false;
        turno_b = false;

        // Inserimento del token in testa

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

    // Metodi di richiesta HTTP

    public void modifyDataPutTorneo(ModifyDataTorneo dataTorneo){

        Call<ResponseTorneo> call = apiInterface.modifyDataPutTorneo(dataTorneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {

                Toast.makeText(ModifySingleTorneo.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {

                Toast.makeText(ModifySingleTorneo.this,"Errore! ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void modifyLuogoPutTorneo(ModifyLuogoTorneo modifyLuogoTorneo){

        Call<ResponseTorneo> call = apiInterface.modifyLuogoPutTorneo(modifyLuogoTorneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {

                Toast.makeText(ModifySingleTorneo.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {

                Toast.makeText(ModifySingleTorneo.this,"Errore! ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void modifyNTurniPutTorneo(ModifyNturniTorneo modifyNturniTorneo){

        Call<ResponseTorneo> call = apiInterface.modifyNTurniPutTorneo(modifyNturniTorneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(ModifySingleTorneo.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(ModifySingleTorneo.this,"Errore! ", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void modifyTurnoPutTorneo(ModifyTurnoTorneo modifyTurnoTorneo){

        Call<ResponseTorneo> call = apiInterface.modifyTurnoPutTorneo(modifyTurnoTorneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(ModifySingleTorneo.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(ModifySingleTorneo.this,"Errore! ", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void modifyNomePutTorneo(ModifyNomeTorneo modifyNomeTorneo){

        Call<ResponseTorneo> call = apiInterface.modifyNomePutTorneo(modifyNomeTorneo);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(ModifySingleTorneo.this,response.body().getRisposta(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(ModifySingleTorneo.this,"Errore! ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void controlloDati(){

        // Viene chiamato una sola volta, ovver quando schiaccio il bottone...
        // Controllo che i dati che passo dalla Activity precedente non siano modificati

            if(nome_finale.equals(nome_iniziale) == false)
                nome_b = true;

            if(luogo_finale.equals(luogo_iniziale) == false)
                luogo_b = true;

            if(data_finale.equals(data_iniziale) == false)
                data_b = true;

            if(turno_finale != turno_iniziale)
                turno_b = true;

            if(turni_finale != turni_iniziale)
                turni_b = true;


        System.out.println("nobe_ b" + nome_b);
        System.out.println("luogo_ b" + luogo_b);
        System.out.println("data_ b" + data_b);
        System.out.println("turno_ b" + turno_b);
        System.out.println("turni_ b" + turni_b);


    }

        // Ciò che succede quando schiaccio il bottone

        public void modifySingleTorneo(View view){

            nome_finale = nome.getText().toString();
            luogo_finale = luogo.getText().toString();
            data_finale = data.getText().toString();
            turni_finale = Integer.parseInt(turni.getText().toString());
            turno_finale = Integer.parseInt(turno.getText().toString());

            System.out.println(nome_finale);
            System.out.println(luogo_finale);
            System.out.println(data_finale);
            System.out.println(turni_finale);
            System.out.println(turno_finale);

            controlloDati();


            // Controllo che i cambi di cambiamento siano true per mandare la richiesta di
            if(nome_b){
                modifyNomeTorneo = new ModifyNomeTorneo(id_iniziale,nome_finale);
                modifyNomePutTorneo(modifyNomeTorneo);
            }

            if(luogo_b){
                modifyLuogoTorneo = new ModifyLuogoTorneo(id_iniziale,luogo_finale);
                modifyLuogoPutTorneo(modifyLuogoTorneo);
            }

            if(data_b){
                modifyDataTorneo = new ModifyDataTorneo(id_iniziale,data_finale);
                modifyDataPutTorneo(modifyDataTorneo);
            }

            if(turno_b){
                modifyTurnoTorneo = new ModifyTurnoTorneo(id_iniziale,turno_finale);
                modifyTurnoPutTorneo(modifyTurnoTorneo);
            }

            if(turni_b){
                modifyNturniTorneo = new ModifyNturniTorneo(id_iniziale,turni_finale);
                modifyNTurniPutTorneo(modifyNturniTorneo);
            }

            goToAmministratoreModifyTorneo();

        }


    public void goToAmministratoreModifyTorneo(){
        Intent intent = new Intent(this, AmministratoreModificaTorneo.class);
        startActivity(intent);
    }
}
