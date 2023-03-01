package com.example.circoloscacchistico.Util;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.circoloscacchistico.Amministratore.response.ResponseGetPartita;
import com.example.circoloscacchistico.R;

import java.util.ArrayList;

public class CustomPartita extends BaseAdapter {

    private ArrayList<ResponseGetPartita> partitaArrayList;
    private Context context;
    private int layout;

    // Genero ora il costruttore

    public CustomPartita(ArrayList<ResponseGetPartita> partitaArrayList, Context context, int layout) {
        this.partitaArrayList = partitaArrayList;
        this.context = context;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return partitaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return partitaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creo un nuovo view Holder nella classe

    private class ViewHolder{

        TextView idPartitatxt,idSfi1txt,idSfi2txt,turnotxt,ris1txt,ris2txt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);

        // id type casting
        viewHolder.idPartitatxt = convertView.findViewById(R.id.viewPartitaId);
        viewHolder.idSfi1txt = convertView.findViewById(R.id.viewIdSfi1);
        viewHolder.idSfi2txt = convertView.findViewById(R.id.viewIdSfi2);
        viewHolder.turnotxt = convertView.findViewById(R.id.viewTurnoPartita);
        viewHolder.ris1txt = convertView.findViewById(R.id.viewRis1);
        viewHolder.ris2txt = convertView.findViewById(R.id.viewRis2);


        ResponseGetPartita partita = partitaArrayList.get(position);

        viewHolder.idPartitatxt.setText(partita.getId_partita().toString());
        viewHolder.idSfi1txt.setText(partita.getId_sfidante1().toString());
        viewHolder.idSfi2txt.setText(partita.getId_sfidante2().toString());
        viewHolder.turnotxt.setText(partita.getTurno().toString());
        viewHolder.ris1txt.setText(Double.toString(partita.getRisultato_sfidante1()));
        viewHolder.ris2txt.setText(Double.toString(partita.getRisultato_sfidante2()));

        if(partita.getTurno()== 1){
            viewHolder.turnotxt.setBackgroundColor(Color.parseColor("#00FF00"));
            viewHolder.idPartitatxt.setBackgroundColor(Color.parseColor("#00FF00"));
            viewHolder.idSfi1txt.setBackgroundColor(Color.parseColor("#00FF00"));
            viewHolder.idSfi2txt.setBackgroundColor(Color.parseColor("#00FF00"));
            viewHolder.ris1txt.setBackgroundColor(Color.parseColor("#00FF00"));
            viewHolder.ris2txt.setBackgroundColor(Color.parseColor("#00FF00"));

        }
        else if(partita.getTurno() == 2){
            viewHolder.turnotxt.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.idPartitatxt.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.idSfi1txt.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.idSfi2txt.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.ris1txt.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.ris2txt.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        else if (partita.getTurno() == 3){
            viewHolder.turnotxt.setBackgroundColor(Color.parseColor("#0000FF"));
            viewHolder.idPartitatxt.setBackgroundColor(Color.parseColor("#0000FF"));
            viewHolder.idSfi1txt.setBackgroundColor(Color.parseColor("#0000FF"));
            viewHolder.idSfi2txt.setBackgroundColor(Color.parseColor("#0000FF"));
            viewHolder.ris1txt.setBackgroundColor(Color.parseColor("#0000FF"));
            viewHolder.ris2txt.setBackgroundColor(Color.parseColor("#0000FF"));
        }

        else if (partita.getTurno() == 4){
            viewHolder.turnotxt.setBackgroundColor(Color.parseColor("#FFF000"));
            viewHolder.idPartitatxt.setBackgroundColor(Color.parseColor("#FFF000"));
            viewHolder.idSfi1txt.setBackgroundColor(Color.parseColor("#FFF000"));
            viewHolder.idSfi2txt.setBackgroundColor(Color.parseColor("#FFF000"));
            viewHolder.ris1txt.setBackgroundColor(Color.parseColor("#FFF000"));
            viewHolder.ris2txt.setBackgroundColor(Color.parseColor("#FFF000"));
        }
        else if (partita.getTurno() == 5){
            viewHolder.turnotxt.setBackgroundColor(Color.parseColor("#00FFF0"));
            viewHolder.idPartitatxt.setBackgroundColor(Color.parseColor("#00FFF0"));
            viewHolder.idSfi1txt.setBackgroundColor(Color.parseColor("#00FFF0"));
            viewHolder.idSfi2txt.setBackgroundColor(Color.parseColor("#00FFF0"));
            viewHolder.ris1txt.setBackgroundColor(Color.parseColor("#00FFF0"));
            viewHolder.ris2txt.setBackgroundColor(Color.parseColor("#00FFF0"));
        }

        return convertView;
    }
}
