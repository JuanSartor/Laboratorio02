package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View;



public class detallePedidoViewHolder {

    public TextView txt_cantidad;
    public TextView txt_producto;
    public RadioButton rdb_seleccion;


    public  detallePedidoViewHolder(View base){

        this.txt_cantidad= (TextView) base.findViewById(R.id.tv_cantidad_producto);
        this.txt_producto= (TextView) base.findViewById(R.id.tv_producto);
        this.rdb_seleccion= (RadioButton) base.findViewById(R.id.rdb_detallePedido);



    }
}
