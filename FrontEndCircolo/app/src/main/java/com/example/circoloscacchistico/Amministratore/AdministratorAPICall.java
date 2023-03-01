package com.example.circoloscacchistico.Amministratore;

import com.example.circoloscacchistico.Amministratore.model.CreateTorneo;
import com.example.circoloscacchistico.Amministratore.model.DeleteTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyDataTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyLuogoTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyNomeTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyNturniTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyStatoTorneo;
import com.example.circoloscacchistico.Amministratore.model.ModifyTurnoTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseClassifica;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;
import com.example.circoloscacchistico.Utente.model.Partita;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AdministratorAPICall {

    // Non inserisco nulla nel corpo, devo avere una lista di tornei...

    @GET("Get")
    Call<ArrayList<ResponseGetTorneo>> createGetListaTornei();

    @POST("Nuovo")
    Call<ResponseTorneo> createPostNuovoTorneo(@Body CreateTorneo dataTorneo);

    @HTTP(method = "DELETE", path = "Elimina", hasBody = true)
    Call<ResponseTorneo> deletePutTorneo(@Body DeleteTorneo deleteTorneo);

    // Metodi per la modifica

    //@HTTP(method = "PUT", path = "ModificaData",hasBody = true)

    @PUT("ModificaData")
    Call<ResponseTorneo> modifyDataPutTorneo(@Body ModifyDataTorneo modifyDataTorneo);

    //@HTTP(method = "PUT", path = "ModificaLuogo",hasBody = true)
    @PUT("ModificaLuogo")
    Call<ResponseTorneo> modifyLuogoPutTorneo(@Body ModifyLuogoTorneo modifyLuogoTorneo);

    //@HTTP(method = "PUT", path = "ModificaNTurni",hasBody = true)
    @PUT("ModificaNTurni")
    Call<ResponseTorneo> modifyNTurniPutTorneo(@Body ModifyNturniTorneo modifyNturniTorneo);

    //@HTTP(method = "PUT", path = "ModificaTurno",hasBody = true)
    @PUT("ModificaTurno")
    Call<ResponseTorneo> modifyTurnoPutTorneo(@Body ModifyTurnoTorneo modifyTurnoTorneo);

    //@HTTP(method = "PUT", path = "ModificaNome",hasBody = true)
    @PUT("ModificaNome")
    Call<ResponseTorneo> modifyNomePutTorneo(@Body ModifyNomeTorneo modifyNomeTorneo);


    // Per la generazione dei turni:

    //ModificaStato @PUT ----> input     id_torneo,in_corso boolean

    // GeneraTurno @PUT ----> input     id_torneo,turno_attuale integer

    @PUT("ModificaStato")
    Call<ResponseTorneo> modifyStatoPutTorneo(@Body ModifyStatoTorneo modifyStatoTorneo);

    @PUT("GeneraTurno")
    Call<ResponseTorneo> GeneraTurno(@Body ModifyTurnoTorneo modifyTurnoTorneo);

    @POST("Classifica")
    Call<ArrayList<ResponseClassifica>> createClassificaTorneo(@Body ModifyTurnoTorneo modifyTurnoTorneo );


}
