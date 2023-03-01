package com.example.circoloscacchistico.Amministratore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.circoloscacchistico.Amministratore.AdministratorPartitaAPICall;
import com.example.circoloscacchistico.Amministratore.model.PartitaRequestById;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetPartita;
import  com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.CustomPartita;
import com.example.circoloscacchistico.Util.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModificaPartitaLista extends AppCompatActivity {

    ListView listViewPartite;

    Context context;
    String token;
    ArrayList<ResponseGetPartita>listaPartite;


    private AdministratorPartitaAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Partita/";

    private  Integer id_b;
    private  String nome_b;
    private  String luogo_b;
    private String data_b;
    private  Integer turni_b;
    private  Integer turno_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_partita_lista);

        // Permessi Per la modifica dei risultati delle partite

        context = this;
        token =  Store.getToken(context);

        // Valori passati dalla activity precedente
        Intent intent = getIntent();
        id_b = intent.getExtras().getInt("id");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");
        turno_b = intent.getExtras().getInt("turno");

        richiestaListaPartite();
    }

    public void richiestaListaPartite(){

        listaPartite = new ArrayList<>();

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


            apiInterface = retrofit.create(AdministratorPartitaAPICall.class);

            // ------------------------ fine retrofit ----------------------

            PartitaRequestById richiesta  = new PartitaRequestById(id_b);
            Call<ArrayList<ResponseGetPartita>> call = apiInterface.createPostGetByTorneoId(richiesta);

            call.enqueue(new Callback<ArrayList<ResponseGetPartita>>() {
                @Override
                public void onResponse(Call<ArrayList<ResponseGetPartita>> call, Response<ArrayList<ResponseGetPartita>> response) {

                    if(response.code() != 200){
                        // Gestione errore

                        Toast.makeText(ModificaPartitaLista.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                        return ;
                    }


                    //listaTornei = response.body();

                    try {
                        ArrayList<ResponseGetPartita> locale = response.body();
                        listViewPartite = (ListView) findViewById(R.id.ListViewPartitaLista);

                        for (int i = 0; i < locale.size(); i++) { // già mi stampa i valori

                            System.out.println(response.body().get(i).getId_partita() +" "+
                                    response.body().get(i).getId_sfidante1()+ " "+
                                    response.body().get(i).getId_sfidante2()+ " "+
                                    response.body().get(i).getTurno()+ " "+
                                    response.body().get(i).getRisultato_sfidante1() + " "+
                                    response.body().get(i).getRisultato_sfidante2());
                            listaPartite.add(response.body().get(i));
                        }

                        CustomPartita custom = new CustomPartita(listaPartite,ModificaPartitaLista.this,R.layout.list_item_partite);
                        listViewPartite.setAdapter(custom);


                        listViewPartite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                ResponseGetPartita partita = listaPartite.get(position);
                                                        // Da cambiare DeleteSingleTorneo
                                Intent intent = new Intent(ModificaPartitaLista.this,ModificaPartitaSingolo.class);

                                intent.putExtra("id_partita",partita.getId_partita());
                                intent.putExtra("id_bianco",partita.getId_sfidante1());
                                intent.putExtra("id_nero",partita.getId_sfidante2());
                                intent.putExtra("turno",partita.getTurno());
                                intent.putExtra("ris_bianco",partita.getRisultato_sfidante1());
                                intent.putExtra("ris_nero",partita.getRisultato_sfidante2());


                                intent.putExtra("id",id_b);
                                intent.putExtra("nome",nome_b);
                                intent.putExtra("luogo",luogo_b);
                                intent.putExtra("data",data_b);
                                intent.putExtra("turni",turni_b);


                                startActivity(intent);

                            }
                        });



                    }catch (Exception e){
                        System.out.println("qualcosa è andato storto ");
                    }

                    Toast.makeText(ModificaPartitaLista.this,"Successo! ", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ArrayList<ResponseGetPartita>> call, Throwable t) {
                    Toast.makeText(ModificaPartitaLista.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();

                }
            });
        }

        public void goToMainAdministrator(View  view){

            Intent intent = new Intent(ModificaPartitaLista.this,MainAmministratoreActivity.class);                            startActivity(intent);
            startActivity(intent);
        }

    public void BottoneUpdateListaPartite(View view){
        richiestaListaPartite();
    }

    }
