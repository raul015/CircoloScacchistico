package com.example.circoloscacchistico.Autenticazione.activity;

import com.example.circoloscacchistico.Autenticazione.*;
import com.example.circoloscacchistico.Autenticazione.response.*;
import com.example.circoloscacchistico.Autenticazione.model.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class PasswordNuovaActivity extends AppCompatActivity {


    EditText editTextNuovaPassword;
    EditText editTextConfermaNuovaPassword;

    Button btnConfermaNuovaPassword;

    private String Token_inviato;
    private String Email_inviata;


    private MyAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        // Creazione delle entità che dovrò utilizzare...

       editTextNuovaPassword = (EditText)findViewById(R.id.PasswordNuova);
       editTextConfermaNuovaPassword = (EditText) findViewById(R.id.ConfermaNuovaPassword);
       btnConfermaNuovaPassword = (Button)findViewById(R.id.btnConfermaNuovaPassword);


       // Informazioni che provengono dall'activity prima --> Token inviato con successo


        Intent intent = getIntent();
        Token_inviato = intent.getExtras().getString("token");
        Email_inviata = intent.getExtras().getString("email");



        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + Token_inviato)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        // Creo un nuovo oggetto Retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(MyAPICall.class);

    }


public void BottoneConfermaNuovaPassword(View view){

    String password = editTextNuovaPassword.getText().toString();
    String nuovaPassword = editTextConfermaNuovaPassword.getText().toString();

    if(controlloDati(password,nuovaPassword)){
        // Tutti i campi sono stati riempiti, anche password coincidenti.

        createPostChangePassword(Email_inviata,password);
        btnConfermaNuovaPassword.setBackgroundColor(Color.parseColor("#00FF00"));
        btnConfermaNuovaPassword.setText("Tutti i campi sono stati rimepiti");
    }
    else{ // L'utente deve inserire tutti i campi per conferma

        btnConfermaNuovaPassword.setBackgroundColor(Color.parseColor("#FF0000"));
        btnConfermaNuovaPassword.setText("Completa l'inserimento dei dati");

    }


}

    public void createPostChangePassword(String email, String password){

        NewPasswordModel newPasswordModel = new NewPasswordModel(email,password);
        Call<ResponseNewPasswordModel> call = apiInterface.createPostChangePassword(newPasswordModel);
        call.enqueue(new Callback<ResponseNewPasswordModel>() {
            @Override
            public void onResponse(Call<ResponseNewPasswordModel> call, Response<ResponseNewPasswordModel> response) {

                if(response.isSuccessful()){
                    Toast.makeText(PasswordNuovaActivity.this,"La nuova password è stata settata : " + response.body().getSuccesso(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PasswordNuovaActivity.this,"La nuova password NON è stata settata: "+ response.body().getSuccesso(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseNewPasswordModel> call, Throwable t) {

                Toast.makeText(PasswordNuovaActivity.this,"error: " + Email_inviata + " " + Token_inviato ,Toast.LENGTH_SHORT).show();


            }

        });


    }




    boolean controlloDati(String password, String nuovaPassword){

        if(!password.isEmpty()){

            if(!nuovaPassword.isEmpty()){

                if(password.equals(nuovaPassword)){
                    return true;

                }

            }
        }

        return false;

    }

    public void FromConfermaPasswordgoToLogin(View view){
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }
}
