package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View;



public class detallePedidoViewHolder {

    TextView txt_cantidad;
    TextView txt_producto;
    TextView txt_precio;
    RadioButton rdb_seleccion;


    public  detallePedidoViewHolder(View base){
        this.txt_cantidad= (TextView) base.findViewById(R.id.tv_cantidad_producto);
        this.txt_producto= (TextView) base.findViewById(R.id.tv_producto);
        this.txt_precio= (TextView) base.findViewById(R.id.tv_precio);
        this.rdb_seleccion= (RadioButton) base.findViewById(R.id.rdb_detallePedido);
    }
}
