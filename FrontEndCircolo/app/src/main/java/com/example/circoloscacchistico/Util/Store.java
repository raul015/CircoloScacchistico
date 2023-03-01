package com.example.circoloscacchistico.Util;

import android.content.Context;
import android.content.SharedPreferences;

// Classe per il salvataggio di dati permanenti su file xml


public class Store {

    private  SharedPreferences sharedPreferences;
    private static String PREFIX = "csc";

    public Store() {
    }

    // Settaggio delle chiavi permanenti

    private static  SharedPreferences getPreferences(Context context){
        // I nomi dei file utilizzeranno tutti questo prefisso
        return context.getSharedPreferences(PREFIX, Context.MODE_PRIVATE);
    }

    public  static  String getUsername(Context context){
        return getPreferences(context).getString("firstname","default_firstname");
    }

    public  static  String getLastname(Context context){
        return getPreferences(context).getString("lastname","default_lastname");
    }

    public  static  String getEmail(Context context){
        return getPreferences(context).getString("email","default_email@");
    }

    public  static  String getId(Context context){
        return getPreferences(context).getString("id","default_id");
    }

    // Poi questo token verrà aggiornato ad ogni Login, oppure vediamo come gestirlo...

    public  static  String getToken(Context context){
        return getPreferences(context).getString("token","default_token");
    }


    public static void setFirstname(Context context, String firstname) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("firstname", firstname);
        editor.commit();
    }

    public static void setLastname(Context context, String lastname) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("lastname", lastname);
        editor.commit();
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("email", email);
        editor.commit();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static void setId(Context context, String id) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("id", id);
        editor.commit();
    }
}
