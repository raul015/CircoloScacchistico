package com.example.circoloscacchistico;
import  com.example.circoloscacchistico.Autenticazione.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.Autenticazione.activity.LogActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //  Corpo tipico della main Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }


    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

}