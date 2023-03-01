package com.example.circoloscacchistico.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.circoloscacchistico.Amministratore.response.ResponseClassifica;
import com.example.circoloscacchistico.R;

import java.util.ArrayList;

public class CustomClassifica extends BaseAdapter {

    private ArrayList<ResponseClassifica> classificaArrayList;
    private Context context;
    private int layout;

    public CustomClassifica(ArrayList<ResponseClassifica> classificaArrayList, Context context, int layout) {
        this.classificaArrayList = classificaArrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return classificaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return classificaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creo un nuovo view Holder nella classe

    private class ViewHolder{

        TextView idTorneotxt,idUsertxt,Firstnametxt,Punteggiotxt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout,null);

        // id type casting
        viewHolder.idTorneotxt = convertView.findViewById(R.id.classificaIdTorneo);
        viewHolder.idUsertxt = convertView.findViewById(R.id.classificaIdUser);
        viewHolder.Firstnametxt = convertView.findViewById(R.id.classificaNome);
        viewHolder.Punteggiotxt = convertView.findViewById(R.id.classificaPunteggio);

        ResponseClassifica classifica = classificaArrayList.get(position);

        viewHolder.idTorneotxt.setText(classifica.getIdtorneo().toString());
        viewHolder.idUsertxt.setText(classifica.getIduser().toString());
        viewHolder.Firstnametxt.setText(classifica.getFirstname());
        viewHolder.Punteggiotxt.setText(Double.toString(classifica.getPunteggio()));

        return convertView;
    }
}
