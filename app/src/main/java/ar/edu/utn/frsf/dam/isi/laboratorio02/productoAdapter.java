package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class productoAdapter extends ArrayAdapter<Producto> {
    private Context ctx;
    private int mSelectedIndex = -1;
    private List<Producto> datos;

    public productoAdapter(Context act, List<Producto> productos){
        super(act,0,productos);
        this.ctx=act;
        this.datos=productos;
    }

    public void setSelectedIndex(int position){
        this.mSelectedIndex = position;
    }

    public Producto getProductoSeleccionado(){
        return datos.get(mSelectedIndex);
    }

    @Override @NonNull
    public View getView(int position, View convertView,@NonNull ViewGroup parent){
        /*INFLATER*/
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View fila = convertView;
        if (fila == null)
            fila = inflater.inflate(R.layout.product_layout,parent,false);
        /*VIEWHOLDER*/
        productoViewHolder holder = (productoViewHolder) fila.getTag();
        if (holder == null){
            holder = new productoViewHolder(fila);
            fila.setTag(holder);
        }
        Producto prod = (Producto) super.getItem(position);
        holder.nombre.setText(prod.getNombre());
        holder.precio.setText("$("+prod.getPrecio().toString()+")");
        if (mSelectedIndex == position)
          holder.seleccion.setChecked(true);
        else
          holder.seleccion.setChecked(false);
        return fila;
    }
}
