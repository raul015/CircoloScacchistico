package com.example.circoloscacchistico.Autenticazione.activity;

import com.example.circoloscacchistico.Autenticazione.*;


import com.example.circoloscacchistico.Autenticazione.response.*;
import com.example.circoloscacchistico.Autenticazione.model.*;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.circoloscacchistico.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrazioneActivity<token_class> extends AppCompatActivity {

    EditText editTextNomeRegistrazione;
    EditText editTextCognomeRegistrazione;
    EditText editTextEmailRegistrazione;
    EditText editTextPasswordRegistrazione;
    EditText editTextConfermaPasswordRegistrazione;

    Button BtnRegistrazione;

    // Parte per retrofit
    private MyAPICall apiInterface;

    Token token_class = new Token("","");


    public static String api = "http://10.0.2.2:8080/api/v1/auth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        // Oggetti per i campi da riempiere nella registrazione
        editTextNomeRegistrazione = (EditText)findViewById(R.id.NomeRegistrazione);
        editTextCognomeRegistrazione = (EditText) findViewById(R.id.CognomeRegistrazione);
        editTextEmailRegistrazione = (EditText) findViewById(R.id.EmailRegistrazione);
        editTextPasswordRegistrazione = (EditText) findViewById(R.id.PasswordRegistrazione);
        editTextConfermaPasswordRegistrazione = (EditText) findViewById(R.id.ConfermaPasswordRegistrazione);
        BtnRegistrazione = (Button) findViewById(R.id.BtnRegistrazione);

        // Devo chiamare l'API per la registrazione

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(MyAPICall.class);

        // Devo chiamare l'API per mandare la mail
    }

    public void BottoneRegistrazione(View v){

        String nome = editTextNomeRegistrazione.getText().toString();
        String cognome = editTextCognomeRegistrazione.getText().toString();
        String email = editTextEmailRegistrazione.getText().toString();
        String password = editTextPasswordRegistrazione.getText().toString();
        String confermaPassword = editTextConfermaPasswordRegistrazione.getText().toString();


        if(controlloDati(nome,cognome,email,password,confermaPassword)){

            Toast.makeText(RegistrazioneActivity.this,"Tutti i campi sono stati riempiti, inizio controllo dati",Toast.LENGTH_SHORT).show();

            DataRegisterModel user = new DataRegisterModel(
                    nome,
                    cognome,
                    email,
                    password,
                    confermaPassword
            );

            //sendNetworkRequest(user);

            createPostRegistrazione(nome,cognome,email,password,confermaPassword);


        }
        else{ // Qui entro soltanto se manca qualche dato
            //BtnRegistrazione.setBackgroundColor(0xFF0000);
            Toast.makeText(RegistrazioneActivity.this,"Non hai riempito tutti i campi....",Toast.LENGTH_SHORT).show();
        }

    }
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }




    // Controlli iniziali, poi verranno completati con controlli più ristretti...

    boolean controlloDati(String nome, String cognome, String email , String password, String confermaPassword){

        if(!nome.isEmpty()){

            if(!cognome.isEmpty()){

                if(!email.isEmpty()){

                    if(!password.isEmpty()){

                        if(!confermaPassword.isEmpty() && password.equals(confermaPassword)){

                            return true;

                        }

                    }
                }

            }
        }

        return false;

    }

    private void createPostRegistrazione(String nome,String cognome,String email,String password, String confpassword){


        DataRegisterModel datareg = new DataRegisterModel(nome,cognome,email,password,confpassword);

        Call<Token> call = apiInterface.createPostRegistrazione(datareg);


        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()){

                    BtnRegistrazione.setBackgroundColor(Color.parseColor("#00FF00"));
                    Toast.makeText(RegistrazioneActivity.this,"Registrazione avvenuta, controlla la mail",Toast.LENGTH_SHORT).show();

                    String messaggio = "Ciao " + nome + " " + cognome + "! \n";
                        messaggio = messaggio + "la registrazione è avvenuta con successo";


                        EmailModel emailModel = new EmailModel(email,"registrazione",messaggio);

                        createPostEmail(emailModel);
                        goToLogin();

                }
                else{
                    BtnRegistrazione.setBackgroundColor(Color.parseColor("#FF0000"));
                    Toast.makeText(RegistrazioneActivity.this,"registrazione non avvenuta:(",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(RegistrazioneActivity.this,"error :(",Toast.LENGTH_SHORT).show();
                token_class.setToken("errore");
                token_class.setSuccesso("fault");

            }
        });


    }

    private void createPostEmail(EmailModel emailModel){


        Call<ResponseEmailModel> callemail = apiInterface.createPostEmail(emailModel);

        callemail.enqueue(new Callback<ResponseEmailModel>() {

            @Override
            public void onResponse(Call<ResponseEmailModel> callemail, Response<ResponseEmailModel> response1) {

                if(response1.isSuccessful()){
                    Toast.makeText(RegistrazioneActivity.this," Registrazione avvenuta con successo",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(RegistrazioneActivity.this,"Non siamo riusciti a mandare la mail per la segnalazione della registrazione",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseEmailModel> callemail, Throwable t) {
                Toast.makeText(RegistrazioneActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void goToLogin() {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }


}