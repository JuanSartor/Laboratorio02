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
    private int mSelectedIndex = -1;
    private List<PedidoDetalle> detalle;

    public detallePedidoAdapter(Context act, List<PedidoDetalle> pedido_detalle){
        super(act,0,pedido_detalle);
        this.ctx=act;
        this.detalle=pedido_detalle;
    }

    public List<PedidoDetalle> getDetallePedido(){
        return this.detalle;
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater_detalle =LayoutInflater.from(this.ctx);
        View fila = convertView;

        if (fila == null)
            fila = inflater_detalle.inflate(R.layout.detalle_pedido_layout, parent, false);

        /*VIEWHOLDER*/
        detallePedidoViewHolder holder = (detallePedidoViewHolder) fila.getTag();
        if (holder == null) {
            holder = new detallePedidoViewHolder(fila);
            fila.setTag(holder);}

        final PedidoDetalle ped_detalle = (PedidoDetalle) super.getItem(position);

        holder.txt_producto.setText(ctx.getString(R.string.tv_producto)+" "+ped_detalle.getProducto().getNombre());
        holder.txt_cantidad.setText(ctx.getString(R.string.tv_cant_producto)+" "+ped_detalle.getCantidad());
        holder.txt_precio.setText(ctx.getString(R.string.tv_precio)+String.valueOf((ped_detalle.getCantidad()*ped_detalle.getProducto().getPrecio())));

        if (mSelectedIndex == position)
            holder.rdb_seleccion.setChecked(true);
        else
            holder.rdb_seleccion.setChecked(false);

        holder.rdb_seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedIndex = position;
                notifyDataSetChanged();
            }
        });
        return fila;

    }



}
