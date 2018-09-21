package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class productoViewHolder {
    TextView nombre;
    TextView precio;
    RadioButton seleccion;

    productoViewHolder(View base){
        this.nombre = (TextView) base.findViewById(R.id.tvNombre);
        this.precio = (TextView) base.findViewById(R.id.tvPrecio);
        this.seleccion = (RadioButton) base.findViewById(R.id.rbProducto);
    }
}
