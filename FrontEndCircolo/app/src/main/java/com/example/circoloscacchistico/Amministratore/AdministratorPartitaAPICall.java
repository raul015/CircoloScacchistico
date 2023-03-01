package com.example.circoloscacchistico.Amministratore;

import com.example.circoloscacchistico.Amministratore.model.CreateTorneo;
import com.example.circoloscacchistico.Amministratore.model.PartitaModify;
import com.example.circoloscacchistico.Amministratore.model.PartitaRequestById;
import com.example.circoloscacchistico.Amministratore.response.ResponseGetPartita;
import com.example.circoloscacchistico.Amministratore.response.ResponsePartita;
import com.example.circoloscacchistico.Amministratore.response.ResponseTorneo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AdministratorPartitaAPICall {

    @POST("GetByTorneoId")
    Call<ArrayList<ResponseGetPartita>> createPostGetByTorneoId(@Body PartitaRequestById richiesta);

    @PUT("ModifyRisultato")
    Call<ResponsePartita> createPutModifyPartita(@Body PartitaModify richiesta);

}
