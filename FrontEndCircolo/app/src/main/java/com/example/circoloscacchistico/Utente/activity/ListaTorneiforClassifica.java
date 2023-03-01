package com.example.circoloscacchistico.Utente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Utente.UserIscrizioneAPICall;
import com.example.circoloscacchistico.Utente.model.ContenitoreInfoById;
import com.example.circoloscacchistico.Utente.model.InfoById;
import com.example.circoloscacchistico.Utente.model.RequestId;
import com.example.circoloscacchistico.Util.CustomTorneo;
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

public class ListaTorneiforClassifica extends AppCompatActivity {

    ListView listViewTornei;
    Context context;
    String token;
    Integer id_user;
    ArrayList<ResponseGetTorneo> listaTornei;
    List<InfoById> listaInfo;
    ContenitoreInfoById contenitore;


    private UserIscrizioneAPICall apiInterface;

    private RequestId requestId;
    public static String api = "http://10.0.2.2:8080/api/v1/Iscrizione/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_torneifor_classifica);

        context = this;
        token =  Store.getToken(context);
        id_user = Integer.valueOf(Store.getId(context));

        System.out.println(id_user); // Id viene retto correttamente
        requestId = new RequestId(id_user);

        RichiestaListaIscrizioni();
    }

    public  void RichiestaListaIscrizioni(){

        listaTornei = new ArrayList<>(); // -> ArrayList di ResponseGetTorneo
        listaInfo = new ArrayList<>();
        contenitore = new ContenitoreInfoById();

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

        apiInterface = retrofit.create(UserIscrizioneAPICall.class);


        // ------------------------ fine retrofit ---------------------- Fino a qua tutto bene

        System.out.println("id prima della richiesta : " + requestId.getId());
        Call<ContenitoreInfoById> call = apiInterface.createPostIscrizioneByUserId(requestId);

        call.enqueue(new Callback<ContenitoreInfoById>() {
            @Override
            public void onResponse(Call<ContenitoreInfoById> call, Response<ContenitoreInfoById> response) {

                if(response.code() != 200){
                    // Gestione errore
                    Toast.makeText(ListaTorneiforClassifica.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                    return ;
                }
                System.out.println("dimensione " + response.body().getLista_tornei().size());

                ArrayList<InfoById> lista_tornei = new ArrayList<>();
                    contenitore.setLista_tornei(response.body().getLista_tornei());
                    listViewTornei = (ListView) findViewById(R.id.ListViewTorneiClassifica);

                    for (int i = 0; i < contenitore.getLista_tornei().size(); i++) { // già mi stampa i valori

                        System.out.println(response.body().getLista_tornei().get(i).getId_torneo() +" "+
                                response.body().getLista_tornei().get(i).getNome_torneo()+ " "+
                                response.body().getLista_tornei().get(i).getData_torneo()+ " "+
                                response.body().getLista_tornei().get(i).getLuogo()+ " "+
                                response.body().getLista_tornei().get(i).getNumero_turni() + " "+
                                response.body().getLista_tornei().get(i).getTurno_attuale());

                        ResponseGetTorneo torneo = new ResponseGetTorneo(
                                response.body().getLista_tornei().get(i).getId_torneo(),
                                response.body().getLista_tornei().get(i).getNome_torneo(),
                                response.body().getLista_tornei().get(i).getData_torneo(),
                                response.body().getLista_tornei().get(i).getLuogo(),
                                response.body().getLista_tornei().get(i).getNumero_turni(),
                                response.body().getLista_tornei().get(i).getTurno_attuale(),
                                response.body().getLista_tornei().get(i).isIn_corso());

                        listaTornei.add(torneo);
                    }

                try {

                    CustomTorneo custom = new CustomTorneo(listaTornei,ListaTorneiforClassifica.this,R.layout.list_item1);

                    listViewTornei.setAdapter(custom);

                    listViewTornei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ResponseGetTorneo torneo = listaTornei.get(position);

                            Intent intent = new Intent(ListaTorneiforClassifica.this, ListaPartiteTorneoClassifica.class);
                            intent.putExtra("id_torneo",torneo.getId());
                            intent.putExtra("nome",torneo.getNome_torneo());
                            intent.putExtra("luogo",torneo.getLuogo());
                            intent.putExtra("data",torneo.getData_torneo());
                            intent.putExtra("turni",torneo.getNumero_turni());
                            intent.putExtra("turno_attuale_torneo",torneo.getTurno_attuale());

                            for(int i=0; i<contenitore.getLista_tornei().size();i++){
                                // Devo verificare quale torneo è stato schiacciato
                                if(contenitore.getLista_tornei().get(i).getId_torneo().equals(torneo.getId())){

                                   intent.putExtra("id_iscrizione",contenitore.getLista_tornei().get(i).getIscrizioni().get(0).getId_iscrizione());
                                }
                            }
                            startActivity(intent);

                        }
                    });

                }catch (Exception e){
                    System.out.println("qualcosa è andato storto ");
                }

                Toast.makeText(ListaTorneiforClassifica.this,"Successo! ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ContenitoreInfoById> call, Throwable t) {
                Toast.makeText(ListaTorneiforClassifica.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void goToMainUtent(View view ){
        Intent intent = new Intent(ListaTorneiforClassifica.this, MainUtenteActivity.class);
        startActivity(intent);
    }

    public void UpdateTornei(View view){
        RichiestaListaIscrizioni();
    }
}