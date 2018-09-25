package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class pedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;

    public pedidoAdapter(Context act, List<Pedido> productos){
        super(act,0,productos);
        this.ctx=act;
    }

    @Override @NonNull
    public View getView(int position, View convertView,@NonNull ViewGroup parent){
        /*INFLATER*/
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View fila = convertView;
        if (fila == null)
            fila = inflater.inflate(R.layout.pedido_layout,parent,false);
        /*VIEWHOLDER*/
        pedidoViewHolder holder = (pedidoViewHolder) fila.getTag();
        if (holder == null){
            holder = new pedidoViewHolder(fila);
            fila.setTag(holder);}
        final Pedido ped = (Pedido) super.getItem(position);

        holder.texto1.setText(ped.toString());

        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ped.setEstado(Pedido.Estado.CANCELADO);
                notifyDataSetChanged();
            }
        });

        holder.texto1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(ctx, NuevoPedido.class);
                i.putExtra("idPedido",ped.getId());
                ctx.startActivity(i);
                return false;}
        });

        return fila;
    }
}
