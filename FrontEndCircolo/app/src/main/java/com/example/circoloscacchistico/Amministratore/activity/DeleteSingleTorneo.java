package com.example.circoloscacchistico.Amministratore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.model.DeleteTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.Autenticazione.activity.LogActivity;
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

public class DeleteSingleTorneo extends AppCompatActivity {

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

    Context context;
    String token;
    DeleteTorneo delete;


    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_single_torneo);

        id = (TextView)findViewById(R.id.singledeleteid);
        nome =(TextView)findViewById(R.id.singledeletenome);
        luogo = (TextView) findViewById(R.id.singledeleteluogo);
        data = (TextView) findViewById(R.id.singledeletedata);
        turni = (TextView) findViewById(R.id.singledeleteturni);
        turno = (TextView) findViewById(R.id.singledeleteturno);
        bottone = (Button) findViewById(R.id.Btnsingledelete);


        // Permessi eliminazione

        context = this;
        token =  Store.getToken(context);

        Intent intent = getIntent();
        id_b = intent.getExtras().getInt("id");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");
        turno_b = intent.getExtras().getInt("turno");

        id.setText(id_b.toString());
        nome.setText(nome_b);
        luogo.setText(luogo_b);
        data.setText(data_b);
        turni.setText(turni_b.toString());
        turno.setText(turno_b.toString());


        delete = new DeleteTorneo(id_b);


        // Retrofit

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

    public void deleteSingleTorneo(View view){

        System.out.println("Bottone schiacciato...");

        // Cambio dopo eliminazione
        Call<ResponseTorneo> call = apiInterface.deletePutTorneo(delete);

        call.enqueue(new Callback<ResponseTorneo>() {
            @Override
            public void onResponse(Call<ResponseTorneo> call, Response<ResponseTorneo> response) {
                Toast.makeText(DeleteSingleTorneo.this,"Successo! ", Toast.LENGTH_SHORT).show();
                goToAmministratoreDeleteTorneo();

            }

            @Override
            public void onFailure(Call<ResponseTorneo> call, Throwable t) {
                Toast.makeText(DeleteSingleTorneo.this,"Qualcosa è andato storto! ", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void goToAmministratoreDeleteTorneo(){
        Intent intent = new Intent(this, AmministratoreDeleteTorneo.class);
        startActivity(intent);
    }


}
