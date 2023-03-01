package com.example.circoloscacchistico.Autenticazione.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.R;

public class PasswordRequestActivity extends AppCompatActivity {

    EditText TokenValue;
    Button BtnToken;

    private String Token_inviato;
    private String Email_inviata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_request);

        TokenValue = (EditText) findViewById(R.id.TokenValue);
        BtnToken =  (Button) findViewById(R.id.btnConfermaToken);
        Intent intent = getIntent();
        Token_inviato = intent.getExtras().getString("token");
        Email_inviata = intent.getExtras().getString("email");

    }


    public void BottoneConfermaCodice(View v){

        String codice = TokenValue.getText().toString();

        if(codice == null){

            // Non si è ancora inserito un codice
            Toast.makeText(PasswordRequestActivity.this,"Non hai inserito nessun codice :(",Toast.LENGTH_SHORT).show();
            BtnToken.setBackgroundColor(Color.parseColor("#FF0000"));

        }
        else if(codice.equals(Token_inviato)){
            BtnToken.setBackgroundColor(Color.parseColor("#00FF00"));
            BtnToken.setText("codice inserito corretto ");
            goToNewPassword();
        }
        else{

            BtnToken.setBackgroundColor(Color.parseColor("#FF0000"));
            BtnToken.setText("codice inserito scorretto ");

        }

    }

    public void goToNewPassword() { // poi qui posso passare dei parametri
        Intent intent = new Intent(this, PasswordNuovaActivity.class);

        intent.putExtra("token",Token_inviato);
        intent.putExtra("email",Email_inviata);
        startActivity(intent);
    }
}
