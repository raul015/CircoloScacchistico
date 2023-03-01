package com.example.circoloscacchistico.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.circoloscacchistico.Amministratore.response.ResponseGetTorneo;
import com.example.circoloscacchistico.R;

import java.util.ArrayList;

public class CustomTorneo extends BaseAdapter {

    private ArrayList<ResponseGetTorneo> torneoArrayList;
    private Context context;
    private int layout;

    // Genero ora il costruttore

    public CustomTorneo(ArrayList<ResponseGetTorneo> torneoArrayList, Context context, int layout) {
        this.torneoArrayList = torneoArrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return torneoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return torneoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creo un nuovo view Holder nella classe

    private class ViewHolder{

        TextView idtxt,nometxt,datatxt,luogotxt,turnitxt,turnotxt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);

        // id type casting
        viewHolder.idtxt = convertView.findViewById(R.id.Deleteidview);
        viewHolder.nometxt = convertView.findViewById(R.id.Deletenomeview);
        viewHolder.datatxt = convertView.findViewById(R.id.Deletedataview);
        viewHolder.luogotxt = convertView.findViewById(R.id.Deleteluogoview);
        viewHolder.turnitxt = convertView.findViewById(R.id.Deleteturniview);
        viewHolder.turnotxt = convertView.findViewById(R.id.Deleteturnoview);

        ResponseGetTorneo torneo = torneoArrayList.get(position);
        viewHolder.idtxt.setText(torneo.getId().toString());
        viewHolder.nometxt.setText(torneo.getNome_torneo());
        viewHolder.datatxt.setText(torneo.getData_torneo());
        viewHolder.luogotxt.setText(torneo.getLuogo());
        viewHolder.turnitxt.setText(Integer.toString(torneo.getNumero_turni()));
        viewHolder.turnotxt.setText(Integer.toString(torneo.getTurno_attuale()));

        System.out.println("Sono dentro la view torneo");
        System.out.println(torneo.getId());
        System.out.println(torneo.getNumero_turni());
        System.out.println(torneo.getTurno_attuale());

        return convertView;
    }
}
