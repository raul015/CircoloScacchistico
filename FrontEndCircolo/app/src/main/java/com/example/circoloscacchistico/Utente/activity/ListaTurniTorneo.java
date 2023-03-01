package com.example.circoloscacchistico.Utente.activity;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetPartita;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.R;
import com.example.circoloscacchistico.Utente.UserIscrizioneAPICall;
import com.example.circoloscacchistico.Utente.model.ContenitoreInfoById;
import com.example.circoloscacchistico.Utente.model.InfoById;
import com.example.circoloscacchistico.Utente.model.RequestId;
import com.example.circoloscacchistico.Util.CustomPartita;
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
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaTurniTorneo extends AppCompatActivity {

    ListView listViewPartite;

    Context context;
    String token;
    ArrayList<ResponseGetTorneo> listaTornei;

    List<InfoById> listaInfo;
    ContenitoreInfoById contenitore;
    ArrayList<ResponseGetPartita> listaPartite;


    private UserIscrizioneAPICall apiInterface;
    public static String api = "http://10.0.2.2:8080/api/v1/Iscrizione/";

    private Integer id_user;

    private  Integer id_torneo_b;
    private  String nome_b;
    private  String luogo_b;
    private String data_b;
    private  Integer turni_b;
    private  Integer turno_b;

    private double ris1;
    private double ris2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_turni_torneo);

        // Permessi Per la modifica dei risultati delle partite

        context = this;
        token =  Store.getToken(context);

        id_user = Integer.valueOf(Store.getId(context));

        // Valori passati dalla activity precedente
        Intent intent = getIntent();
        id_torneo_b = intent.getExtras().getInt("id_torneo");
        nome_b = intent.getExtras().getString("nome");
        luogo_b = intent.getExtras().getString("luogo");
        data_b = intent.getExtras().getString("data");
        turni_b = intent.getExtras().getInt("turni");
        turno_b = intent.getExtras().getInt("turno");

        richiestaListaPartite();
    }

    public void richiestaListaPartite(){

        listaTornei = new ArrayList<>(); // -> ArrayList di ResponseGetTorneo
        listaInfo = new ArrayList<>();
        listaPartite = new ArrayList<>();

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

        // ------------------------ fine retrofit ----------------------

        RequestId richiesta  = new RequestId(id_user);
        Call<ContenitoreInfoById> call = apiInterface.createPostIscrizioneByUserId(richiesta);

        call.enqueue(new Callback<ContenitoreInfoById>() {
            @Override
            public void onResponse(Call<ContenitoreInfoById> call, Response<ContenitoreInfoById> response) {

                if(response.code() != 200){
                    // Gestione errore

                    Toast.makeText(ListaTurniTorneo.this,"Errore 200, devo gestirlo ", Toast.LENGTH_SHORT).show();
                    return ;
                }


                //listaTornei = response.body();
                System.out.println("dimensione contenitore : " + response.body().getLista_tornei().size());

                try {
                    contenitore = response.body();
                    System.out.println("dimensione contenitore : " + contenitore.getLista_tornei().size());

                    listViewPartite = (ListView) findViewById(R.id.ListViewTurniTorneo);
                    for (int i = 0; i < contenitore.getLista_tornei().size(); i++) { // già mi stampa i valori

                        for(int j = 0; j<contenitore.getLista_tornei().get(i).getPartite().size();j++){

                            if( contenitore.getLista_tornei().get(i).getPartite().get(j).getId_sfidante1().equals(id_user)  ||
                                contenitore.getLista_tornei().get(i).getPartite().get(j).getId_sfidante2().equals(id_user) ){
                                System.out.println("Trovato id partecipante");


                                if(contenitore.getLista_tornei().get(i).getPartite().get(j).getRisultato_sfidante1() !=null ||
                                        contenitore.getLista_tornei().get(i).getPartite().get(j).getRisultato_sfidante2() !=null ){

                                    ris1 = contenitore.getLista_tornei().get(i).getPartite().get(j).getRisultato_sfidante1();
                                    ris2 = contenitore.getLista_tornei().get(i).getPartite().get(j).getRisultato_sfidante2();
                                }
                                else{
                                    ris1 = 0.0;
                                    ris2 = 0.0;
                                }

                                if(contenitore.getLista_tornei().get(i).getId_torneo().equals(id_torneo_b)){
                                    ResponseGetPartita partita_locale = new ResponseGetPartita(
                                            contenitore.getLista_tornei().get(i).getPartite().get(j).getId_partita(),
                                            contenitore.getLista_tornei().get(i).getPartite().get(j).getId_sfidante1(),
                                            contenitore.getLista_tornei().get(i).getPartite().get(j).getId_sfidante2(),
                                            contenitore.getLista_tornei().get(i).getPartite().get(j).getTurno(),
                                            ris1,
                                            ris2);
                                    listaPartite.add(partita_locale);
                                }
                                else{
                                    // Non aggiunto niente
                                    System.out.println("non aggiungo niente");
                                }

                            }

                        }
                    }

                    CustomPartita custom = new CustomPartita(listaPartite,ListaTurniTorneo.this,R.layout.list_item_partite);
                    listViewPartite.setAdapter(custom);

                }catch (Exception e){
                    System.out.println("qualcosa è andato storto ");
                }

                Toast.makeText(ListaTurniTorneo.this,"Successo! ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ContenitoreInfoById> call, Throwable t) {
                Toast.makeText(ListaTurniTorneo.this,"Fallimento con il caricamento dei dati", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void goToMainUtent(View  view){

        Intent intent = new Intent(ListaTurniTorneo.this, MainUtenteActivity.class);                            startActivity(intent);
        startActivity(intent);
    }

    public void BottoneUpdateListaTurni(View view){
        richiestaListaPartite();
    }
}