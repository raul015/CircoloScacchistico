package com.example.circoloscacchistico.Amministratore.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.CustomTorneo;
import com.example.circoloscacchistico.Util.Store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AmministratoreGeneraTurno extends AppCompatActivity {

    ListView listViewTurni;
    Context context;
    String token;
    ArrayList<ResponseGetTorneo> listaTornei;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amministratore_genera_turno);

        context = this;
        token =  Store.getToken(context);

        RichiestaListaTornei();

    }

    public void RichiestaListaTornei(){

        listaTornei = new ArrayList<>(); // -> ArrayList di ResponseGetTorneo

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


        Call<ArrayList<ResponseGetTorneo>> call = apiInterface.createGetListaTornei();

        call.enqueue(new Callback<ArrayList<ResponseGetTorneo>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseGetTorneo>> call, Response<ArrayList<ResponseGetTorneo>> response) {

                if(response.code() != 200){
                    // Gestione errore

                    Toast.makeText(AmministratoreGeneraTurno.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                    return ;
                }


                //listaTornei = response.body();

                try {
                    ArrayList<ResponseGetTorneo> locale = response.body();
                    listViewTurni = (ListView) findViewById(R.id.ListViewTurni);

                    for (int i = 0; i < locale.size(); i++) { // già mi stampa i valori

                        System.out.println(response.body().get(i).getId() +" "+
                                response.body().get(i).getNome_torneo()+ " "+
                                response.body().get(i).getData_torneo()+ " "+
                                response.body().get(i).getLuogo()+ " "+
                                response.body().get(i).getNumero_turni() + " "+
                                response.body().get(i).getTurno_attuale());
                        listaTornei.add(response.body().get(i));
                    }

                    CustomTorneo custom = new CustomTorneo(listaTornei,AmministratoreGeneraTurno.this,R.layout.list_item1);
                    listViewTurni.setAdapter(custom);


                    listViewTurni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ResponseGetTorneo torneo = listaTornei.get(position);
                            Intent intent = new Intent(AmministratoreGeneraTurno.this,GeneraSingoloTurno.class);

                            intent.putExtra("id",torneo.getId());
                            intent.putExtra("nome",torneo.getNome_torneo());
                            intent.putExtra("luogo",torneo.getLuogo());
                            intent.putExtra("data",torneo.getData_torneo());
                            intent.putExtra("turni",torneo.getNumero_turni());
                            intent.putExtra("turno",torneo.getTurno_attuale());
                            intent.putExtra("stato",torneo.isIn_corso());

                            System.out.println("stato del torneo...");
                            System.out.println(torneo.isIn_corso());
                            startActivity(intent);

                        }
                    });



                }catch (Exception e){
                    System.out.println("qualcosa è andato storto ");
                }

                Toast.makeText(AmministratoreGeneraTurno.this,"Successo! ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ArrayList<ResponseGetTorneo>> call, Throwable t) {
                Toast.makeText(AmministratoreGeneraTurno.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void  BottoneUpdateListaIscrizioni(View view){
        RichiestaListaTornei();
    }

    public void goToMainAdministrator(View view){
        Intent intent = new Intent(AmministratoreGeneraTurno.this,MainAmministratoreActivity.class);
        startActivity(intent);
    }
}