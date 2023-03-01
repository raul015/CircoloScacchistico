package com.example.circoloscacchistico.Utente;

import com.example.circoloscacchistico.Amministratore.model.ModifyTurnoTorneo;
import com.example.circoloscacchistico.Amministratore.response.ResponseClassifica;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserPartitaAPICall {

    @POST("Classifica")
    Call<ArrayList<ResponseClassifica>> createClassificaTorneo(@Body ModifyTurnoTorneo modifyTurnoTorneo );

}
