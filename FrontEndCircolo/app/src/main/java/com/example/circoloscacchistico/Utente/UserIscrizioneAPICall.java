package com.example.circoloscacchistico.Utente;

import com.example.circoloscacchistico.Utente.model.ContenitoreInfoById;
import com.example.circoloscacchistico.Utente.model.IscrizioneTorneo;
import com.example.circoloscacchistico.Utente.model.RequestId;
import com.example.circoloscacchistico.Utente.response.ResponseIscrizione;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface UserIscrizioneAPICall {

/*
    @GET("Get")
    Call<ArrayList<ResponseGetIscrizione>> createGetListaIscrizioni();
 */

    @POST ("Nuovo")
    Call<ResponseIscrizione> createPostIscrizione(@Body IscrizioneTorneo iscrizioneTorneo);

    @POST ("GetByUserId")
    Call<ContenitoreInfoById> createPostIscrizioneByUserId(@Body RequestId requestId);

    @HTTP(method = "DELETE", path = "EliminaIscrizione", hasBody = true)
    Call<ResponseIscrizione> deleteIscrizioneTorneo(@Body RequestId requestId);


}
