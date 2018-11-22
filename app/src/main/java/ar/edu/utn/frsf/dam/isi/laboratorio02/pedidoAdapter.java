package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class pedidoAdapter extends ArrayAdapter<Pedido> {
    private Context ctx;
    private List<Pedido> datos;
    private PedidoDao pedidoDao;

    public pedidoAdapter(Context act, List<Pedido> pedidos){
        super(act,0,pedidos);
        this.ctx=act;
        this.datos=pedidos;
        this.pedidoDao = MyDb.getInstance(act).getPedidoDao();
    }

    @Override @NonNull
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {
        /*INFLATER*/
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View fila = convertView;

        if (fila == null)
            fila = inflater.inflate(R.layout.fila_historial, parent, false);

        /*VIEWHOLDER*/
        pedidoViewHolder holder = (pedidoViewHolder) fila.getTag();
        if (holder == null) {
            holder = new pedidoViewHolder(fila);
            fila.setTag(holder);}

        final Pedido ped = (Pedido) super.getItem(position);

        double costoTotal=0;
        int temp,cantTotal=0;
        for (PedidoDetalle pd: ped.getDetalle()) {
            temp= pd.getCantidad();
            costoTotal = costoTotal + (pd.getProducto().getPrecio())*(temp);
            cantTotal = cantTotal + temp; }

        holder.txtMail.setText(ctx.getString(R.string.mailFilaHistorial)+" "+ped.getMailContacto());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        holder.txtFechayHora.setText(ctx.getString(R.string.fyhFilaHistorial)+" "+sdf.format(ped.getFecha()));

        holder.txtCosto.setText(ctx.getString(R.string.costoFilaHistorial)+" $"+String.format("%.3f",costoTotal));
        holder.txtCantidad.setText(ctx.getString(R.string.cantidadFilaHistorial)+" "+Integer.toString(cantTotal));

        switch (ped.getEstado()){
            case LISTO:
                holder.txtEstado.setTextColor(Color.DKGRAY);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Listo");
                holder.btnCancelarPedido.setVisibility(View.INVISIBLE);break;
            case ENTREGADO:
                holder.txtEstado.setTextColor(Color.BLUE);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Entregado");
                holder.btnCancelarPedido.setVisibility(View.INVISIBLE);break;
            case CANCELADO:
                holder.txtEstado.setTextColor(Color.RED);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Cancelado");
                holder.btnCancelarPedido.setVisibility(View.INVISIBLE);break;
            case RECHAZADO:
                holder.txtEstado.setTextColor(Color.RED);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Rechazado");
                holder.btnCancelarPedido.setVisibility(View.INVISIBLE);break;
            case ACEPTADO:
                holder.txtEstado.setTextColor(Color.GREEN);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Aceptado");break;
            case EN_PREPARACION:
                holder.txtEstado.setTextColor(Color.MAGENTA);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"En Preparacion");break;
            case REALIZADO:
                holder.txtEstado.setTextColor(Color.BLUE);
                holder.txtEstado.setText(ctx.getString(R.string.estadoFilaHistorial)+" "+"Realizado");break;}

        if (ped.getRetirar())
            holder.imagenRetira.setImageResource(R.drawable.b);
        else
            holder.imagenRetira.setImageResource(R.drawable.a);

        holder.btnCancelarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //El caso if seriviria si se viera el boton
                //de cancelar tambien en los pedidos que no se pueden cancelar.
                //Actualmente no se ve por lo que se hace arriba en el switch con setVisibility().

                if (ped.getEstado()==Pedido.Estado.RECHAZADO ||
                        ped.getEstado()==Pedido.Estado.LISTO ||
                        ped.getEstado()==Pedido.Estado.ENTREGADO)
                    {Toast mensaje = Toast.makeText(ctx.getApplicationContext(),
                            "No se puede cancelar este pedido!", Toast.LENGTH_SHORT);
                    mensaje.show();}
                else{
                    ped.setEstado(Pedido.Estado.CANCELADO);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            pedidoDao.update(ped);
                        }
                    });
                }
                notifyDataSetChanged();
            }
        });

        holder.btnDetallePedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, NuevoPedido.class);
                i.putExtra("origen",1);
                i.putExtra("idPedido",ped.getId());
                ctx.startActivity(i);
            }
        });

        holder.layoutAgrupador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(ctx, NuevoPedido.class);
                i.putExtra("idPedido",ped.getId());
                ctx.startActivity(i);
                return false;}});
        return fila;
    }
}
