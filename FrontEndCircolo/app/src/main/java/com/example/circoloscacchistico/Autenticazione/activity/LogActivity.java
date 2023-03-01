package com.example.circoloscacchistico.Autenticazione.activity;

import com.example.circoloscacchistico.Autenticazione.*;
import com.example.circoloscacchistico.Autenticazione.response.*;
import com.example.circoloscacchistico.Autenticazione.model.*;

import androidx.appcompat.app.AppCompatActivity;

// Import per lo storage dei dati...
import android.content.Context;

import com.example.circoloscacchistico.Util.Store;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circoloscacchistico.Amministratore.activity.MainAmministratoreActivity;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Utente.activity.MainUtenteActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogActivity extends AppCompatActivity {

    Context context;

    EditText editTextEmailLogin;
    EditText editTextPasswordLogin;
    TextView viewPasswordDimenticata;
    Button BtnLogin;

    // Retrofit Builder authenticate
    private MyAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/auth/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        context = this;


        editTextEmailLogin = (EditText) findViewById(R.id.EmailLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.PasswordLogin);
        BtnLogin = (Button) findViewById(R.id.BtnLogin);
        viewPasswordDimenticata = (TextView) findViewById(R.id.PasswordDimenticataLogin);

        // Devo chiamare l'API

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(MyAPICall.class);
    }


    public void BottoneLogin(View v){

        String email = editTextEmailLogin.getText().toString();

        String password = editTextPasswordLogin.getText().toString();

        if(controlloDati(email,password)){
           // createPost(email,password);
            createPostLogin(email,password);

            Toast.makeText(LogActivity.this,"Tutti i dati sono stati inseriti, inizio controllo",Toast.LENGTH_SHORT).show();


            // Per andare ad una nuova activity

        }
        else {
            Toast.makeText(LogActivity.this,"Non hai riempito tutti i campi",Toast.LENGTH_SHORT).show();

            BtnLogin.setBackgroundColor(Color.parseColor("#FF0000"));
            BtnLogin.setText("Login fallito");
        }

    }

    public void goToAdministratorMain(){

        Intent intent = new Intent(this,MainAmministratoreActivity.class);
        startActivity(intent);
    }

    public void goToUtentMain(){

        Intent intent = new Intent(this, MainUtenteActivity.class);
        startActivity(intent);

    }
    public void goToRegistrazione(View view) {
        Intent intent = new Intent(this, RegistrazioneActivity.class);
        startActivity(intent);
    }

    public void goToPasswordDimenticata(View view) {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    public void ViewPassword(View v){

        viewPasswordDimenticata.setText("Hai cliccato");

    }

    boolean controlloDati(String email, String password){

        if(!email.isEmpty()) {

            if (!password.isEmpty()) {

                return true;
            }
        }
        return false;
    }

    private static String token;
    private static String firstname;
    private static String lastname;
    private static Integer id;
    private static Role role;


    /*
    private void createPost(String email, String password){

        DataLoginModel datalog = new DataLoginModel(email,password);

        Call<Token> call = apiInterface.createPost(datalog);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()){
                    Toast.makeText(LogActivity.this,"login corretto : " + response.body().getToken(),Toast.LENGTH_SHORT).show();
                    token = response.body().getToken();
                }
                else{
                    Toast.makeText(LogActivity.this,"login non corretto :(",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LogActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

    private void createPostLogin(String email, String password){


        DataLoginModel datalog = new DataLoginModel(email,password);

        Call<ResponseLoginModel> call = apiInterface.createPostLogin(datalog);

        call.enqueue(new Callback<ResponseLoginModel>() {

            @Override
            public void onResponse(Call<ResponseLoginModel> call, Response<ResponseLoginModel> response) {

                if(response.isSuccessful()){

                    BtnLogin.setBackgroundColor(Color.parseColor("#00FF00"));
                    BtnLogin.setText("Login effettuato");
                    Toast.makeText(LogActivity.this,"Token : " + response.body().getToken(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(LogActivity.this,"Id : " + response.body().getId(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(LogActivity.this,"Role : " + response.body().getRole(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(LogActivity.this,"Firstname : " + response.body().getFirstname(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(LogActivity.this,"Lastname : " + response.body().getLastname(),Toast.LENGTH_SHORT).show();


                    token = response.body().getToken();
                    id = response.body().getId();
                    role = response.body().getRole();
                    firstname = response.body().getFirstname();
                    lastname = response.body().getLastname();

                    Store.setId(context,String.valueOf(id));
                    Store.setFirstname(context,firstname);
                    Store.setLastname(context,lastname);
                    Store.setToken(context,token);
                    Store.setEmail(context,email);


                    if(role ==Role.ADMIN){
                        goToAdministratorMain();
                    }

                    if(role == Role.USER){
                        goToUtentMain();
                    }


                }
                else{
                    Toast.makeText(LogActivity.this,"Errore nei dati inseriti",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLoginModel> call, Throwable t) {
                Toast.makeText(LogActivity.this,"errore",Toast.LENGTH_SHORT).show();
                BtnLogin.setBackgroundColor(Color.parseColor("#FF0000"));
                BtnLogin.setText("Login fallito");
            }
        });

    }


}