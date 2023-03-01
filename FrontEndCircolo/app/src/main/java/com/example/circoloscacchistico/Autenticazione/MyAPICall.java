package com.example.circoloscacchistico.Autenticazione;

import  com.example.circoloscacchistico.Autenticazione.model.*;
import  com.example.circoloscacchistico.Autenticazione.response.*;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MyAPICall {

    // Separo ciò che rimane fisso da cioò che varia nei link
    // http://localhost:8080/api/v1/auth/   authenticate

    // RIFERIMENTO,

    /*
    @POST("authenticate")
    Call<Token> createPost(@Body DataLoginModel datalog);
     */

    @POST("authenticate")
    Call<ResponseLoginModel> createPostLogin(@Body DataLoginModel datalog);

    //mi restituisce la stringa token e successo

    @POST("register")
    Call<Token> createPostRegistrazione(@Body DataRegisterModel dataRegisterModel);

    // mi restituisce la stringa success
    // Call<ResponseEmailModel> createPostEmail(@Body EmailModel emailModel, @Header("Authorization") String basicToken);

    @POST("send-email")
    Call<ResponseEmailModel> createPostEmail(@Body EmailModel emailModel);

    // mi restituisce l'oggetto token

    @POST("password")
    Call<Token> createPostPassword(@Body DataPasswordModel dataPasswordModel);

    @PUT("change-password")
    Call<ResponseNewPasswordModel> createPostChangePassword(@Body NewPasswordModel passwordModel);

}
