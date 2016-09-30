package br.com.devmaker.finances.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.devmaker.finances.model.Finance;
import br.com.devmaker.finances.R;

/**
 * Created by Dev_Maker on 27/04/2016.
 */
public class RelatorioAdapter extends BaseAdapter{

    private ArrayList<Finance> objects;
    private Context context;

    public RelatorioAdapter(Context context, ArrayList<Finance> objects) {
        this.objects = objects;
        this.context = context;
    }




    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        ViewHolder vh;
        if (v == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_relatorio, null);

            vh.nome = (TextView) v.findViewById(R.id.textNome);
            vh.price = (TextView) v.findViewById(R.id.textValor);

            v.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        final Finance finance = objects.get(position);
        vh.nome.setText(finance.getNome());
        vh.price.setText("R$" + String.valueOf(finance.getValor()));

        // the view must be returned to our activity
        return v;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Finance getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        public TextView nome;
        public TextView price;
    }

}
