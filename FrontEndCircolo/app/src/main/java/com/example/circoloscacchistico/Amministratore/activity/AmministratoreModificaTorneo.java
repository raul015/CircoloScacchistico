package com.example.circoloscacchistico.Amministratore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.circoloscacchistico.Amministratore.AdministratorAPICall;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Util.CustomTorneo;
import com.example.circoloscacchistico.Util.Store;

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

public class AmministratoreModificaTorneo extends AppCompatActivity {


    ListView listViewTornei;
    Context context;
    String token;
    ArrayList<ResponseGetTorneo> listaTornei;

    private AdministratorAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Torneo/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amministratore_modifica_torneo);


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

                    Toast.makeText(AmministratoreModificaTorneo.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                    return ;
                }


                //listaTornei = response.body();

                try {
                    ArrayList<ResponseGetTorneo> locale = response.body();
                    listViewTornei = (ListView) findViewById(R.id.ListViewTornei);

                    for (int i = 0; i < locale.size(); i++) { // già mi stampa i valori

                        System.out.println(response.body().get(i).getId() +" "+
                                response.body().get(i).getNome_torneo()+ " "+
                                response.body().get(i).getData_torneo()+ " "+
                                response.body().get(i).getLuogo()+ " "+
                                response.body().get(i).getNumero_turni() + " "+
                                response.body().get(i).getTurno_attuale());
                        listaTornei.add(response.body().get(i));
                    }

                    CustomTorneo custom = new CustomTorneo(listaTornei,AmministratoreModificaTorneo.this,R.layout.list_item1);
                    listViewTornei.setAdapter(custom);


                    listViewTornei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ResponseGetTorneo torneo = listaTornei.get(position);

                            Intent intent = new Intent(AmministratoreModificaTorneo.this,ModifySingleTorneo.class);

                            intent.putExtra("id",torneo.getId());
                            intent.putExtra("nome",torneo.getNome_torneo());
                            intent.putExtra("luogo",torneo.getLuogo());
                            intent.putExtra("data",torneo.getData_torneo());
                            intent.putExtra("turni",torneo.getNumero_turni());
                            intent.putExtra("turno",torneo.getTurno_attuale());
                            startActivity(intent);

                        }
                    });


                }catch (Exception e){
                    System.out.println("qualcosa è andato storto ");
                }

                Toast.makeText(AmministratoreModificaTorneo.this,"Successo! ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ArrayList<ResponseGetTorneo>> call, Throwable t) {
                Toast.makeText(AmministratoreModificaTorneo.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public  void  BottoneUpdateDelete(View view){
        RichiestaListaTornei();
    }

    public void goToMainAdministrator(View view){
        Intent intent = new Intent(AmministratoreModificaTorneo.this,MainAmministratoreActivity.class);
        startActivity(intent);

    }

}
