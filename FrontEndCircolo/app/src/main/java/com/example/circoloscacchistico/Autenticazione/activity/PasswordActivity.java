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

public class PasswordActivity extends AppCompatActivity {

    EditText NomePasswordDimenticata;
    EditText CognomePasswordDimenticata;
    EditText EmailPasswordDimenticata;

    Button  BtnPasswordDimenticata;

    // Parte per retrofit
    private MyAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/auth/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        NomePasswordDimenticata = (EditText) findViewById(R.id.NomePasswordDimenticata);
        CognomePasswordDimenticata = (EditText) findViewById(R.id.CognomePasswordDimenticata);
        EmailPasswordDimenticata = (EditText) findViewById(R.id.EmailPasswordDimenticata);

        BtnPasswordDimenticata = (Button) findViewById(R.id.BtnPasswordDimenticata);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(MyAPICall.class);

        }


    public void BottonePasswordDimenticata(View v){

        String nome = NomePasswordDimenticata.getText().toString();
        String cognome = CognomePasswordDimenticata.getText().toString();
        String email = EmailPasswordDimenticata.getText().toString();

        if(controlloDati(nome,cognome,email)){

            BtnPasswordDimenticata.setBackgroundColor(Color.parseColor("#00FF00"));
            BtnPasswordDimenticata.setText("Hai riempito tutti i campi");
            createPostPassword(nome,cognome,email);


        }
        else{ // Qui entro soltanto se manca qualche dato


            BtnPasswordDimenticata.setBackgroundColor(Color.parseColor("#FF0000"));
            BtnPasswordDimenticata.setText("Non hai riempito tutti i campi");

            //BtnPasswordDimenticata.setBackground(ContextCompat.getDrawable(this,R.drawable.input_red_bg));
            //BtnPasswordDimenticata.setText("errore nel riempimento dei campi");
        }

    }



    boolean controlloDati(String nome, String cognome, String email){

        if(!nome.isEmpty()){

            if(!cognome.isEmpty()){

                if(!email.isEmpty()){

                    return true;
                }

            }
        }

        return false;

    }


    private void createPostPassword(String nome, String cognome, String email){

        DataPasswordModel user = new DataPasswordModel(
                nome,
                cognome,
                email
        );


        Call<Token> callpassword = apiInterface.createPostPassword(user);

        callpassword.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> callpassword, Response<Token> response) {

                if(response.isSuccessful()){

                    BtnPasswordDimenticata.setText("Dati inviati");

                    if(response.body().getSuccesso().equals("fault")){

                        Toast.makeText(PasswordActivity.this,"hai sbagliato ad inserire: "+ response.body().getToken() + "scorretto",Toast.LENGTH_SHORT).show();

                    }

                    else{
                        String messaggio = "Ciao " + nome + " " + cognome + "! \n";

                        messaggio = messaggio + "Inserisci il seguente codice per poter cambiare la tua password \n";
                        messaggio = messaggio + response.body().getToken();


                        EmailModel emailModel = new EmailModel(email,"Recupero password",messaggio);

                        String basicToken = "Bearer " + response.body().getToken();
                        createPostEmail(emailModel,basicToken,response.body().getToken(),response.body().getSuccesso());

                    }

                }
                else{
                    Toast.makeText(PasswordActivity.this,"Dati inseriti scorretti",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Token> callpassword, Throwable t) {
                Toast.makeText(PasswordActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void createPostEmail(EmailModel emailModel,String token,String token_nob, String successo){


        Call<ResponseEmailModel> callemail = apiInterface.createPostEmail(emailModel);

        callemail.enqueue(new Callback<ResponseEmailModel>() {

            @Override
            public void onResponse(Call<ResponseEmailModel> callemail, Response<ResponseEmailModel> response1) {

                if(response1.isSuccessful()){

                    if(response1.body().getSuccesso().equals(successo)){

                        Toast.makeText(PasswordActivity.this," Email invata con successo : "+ successo,Toast.LENGTH_SHORT).show();
                        goToPasswordRequest(token_nob, emailModel.getTo());
                    }
                    else{
                        Toast.makeText(PasswordActivity.this," Errore con l'invio del codice : "+ successo,Toast.LENGTH_SHORT).show();

                    }






                }
                else{
                    Toast.makeText(PasswordActivity.this,"Non siamo riusciti a mandare la mail",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseEmailModel> callemail, Throwable t) {
                Toast.makeText(PasswordActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void goToPasswordRequest(String token, String email) {

            Intent intent = new Intent(this, PasswordRequestActivity.class);
            intent.putExtra("token",token);
            intent.putExtra("email",email);
            startActivity(intent);


    }

    public void goToLoginPass(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }
}