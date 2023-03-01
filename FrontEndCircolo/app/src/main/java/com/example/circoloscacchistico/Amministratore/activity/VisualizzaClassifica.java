package com.example.circoloscacchistico.Amministratore.activity;
import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.model.ModifyTurnoTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseClassifica;
import com.example.circoloscacchistico.Autenticazione.activity.PasswordActivity;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.CustomClassifica;
import com.example.circoloscacchistico.Util.Store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisualizzaClassifica extends AppCompatActivity {

    ListView listViewClassifica;

    Context context;
    String token;
    ArrayList<ResponseClassifica> listaClassifica;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";

    private  Integer id_torneo_b;
    private  Integer turno_attuale_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_classifica);

        context = this;
        token =  Store.getToken(context);

        // Valori passati dalla activity precedente
        Intent intent = getIntent();
        id_torneo_b = intent.getExtras().getInt("id_torneo");
        turno_attuale_b = intent.getExtras().getInt("turno_attuale_torneo");

        richiestaListaClassifica();
    }


    public void richiestaListaClassifica(){

        listaClassifica = new ArrayList<>();
        // ----------------------- inizio retrofit ----------------------
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

        // ------------------------ fine retrofit ----------------------

        ModifyTurnoTorneo richiesta  = new ModifyTurnoTorneo(id_torneo_b,turno_attuale_b);

        Call<ArrayList<ResponseClassifica>> call = apiInterface.createClassificaTorneo(richiesta);

        call.enqueue(new Callback<ArrayList<ResponseClassifica>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseClassifica>> call, Response<ArrayList<ResponseClassifica>> response) {

                if(response.code() != 200){
                    // Gestione errore

                    Toast.makeText(VisualizzaClassifica.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                    return ;
                }

                //listaTornei = response.body();

                try {

                    ArrayList<ResponseClassifica> locale = response.body();
                    listViewClassifica = (ListView) findViewById(R.id.ListViewClassifica); // da decidere il nome
                    System.out.println(id_torneo_b + " - " +  turno_attuale_b);
                    for (int i = 0; i < locale.size(); i++) { // già mi stampa i valori
                        System.out.println(locale.get(i).getFirstname());
                        System.out.println(locale.get(i).getPunteggio());
                        listaClassifica.add(locale.get(i));
                    }

                    CustomClassifica custom = new CustomClassifica(listaClassifica,VisualizzaClassifica.this,R.layout.list_item_classifica);
                    listViewClassifica.setAdapter(custom);


                }catch (Exception e){
                    System.out.println("qualcosa è andato storto ");
                }

                Toast.makeText(VisualizzaClassifica.this,"Successo! ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseClassifica>> call, Throwable t) {
                Toast.makeText(VisualizzaClassifica.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void BottoneUpdateClassificaTorneo(View view){
        richiestaListaClassifica();
    }

    public  void goToMainAdministrator(View view){
        Intent intent = new Intent(this, MainAmministratoreActivity.class);
        startActivity(intent);
    }

}