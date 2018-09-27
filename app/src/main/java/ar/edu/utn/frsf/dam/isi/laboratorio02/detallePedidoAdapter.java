package ar.edu.utn.frsf.dam.isi.laboratorio02;

import java.util.List;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;



public class detallePedidoAdapter extends ArrayAdapter<PedidoDetalle> {

    private Context ctx;
    private List<PedidoDetalle> detalle;

    public detallePedidoAdapter(Context act, List<PedidoDetalle> pedido_detalle){
        super(act,0,pedido_detalle);
        this.ctx=act;
        this.detalle=pedido_detalle;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater_detalle =LayoutInflater.from(this.ctx);
        View fila = convertView;

        if (fila == null)
            fila = inflater_detalle.inflate(R.layout.fila_historial, parent, false);

        /*VIEWHOLDER*/
        detallePedidoViewHolder holder = (detallePedidoViewHolder) fila.getTag();
        if (holder == null) {
            holder = new detallePedidoViewHolder(fila);
            fila.setTag(holder);}

        final PedidoDetalle ped_detalle = (PedidoDetalle) getItem(position);

        holder.txt_producto.setText(ctx.getString(R.string.tv_producto));
        holder.txt_cantidad.setText(ctx.getString(R.string.tv_cant_producto));
        holder.rdb_seleccion.setText(ctx.getString(R.string.rdb_detallePedido));


        return fila;

    }



}
