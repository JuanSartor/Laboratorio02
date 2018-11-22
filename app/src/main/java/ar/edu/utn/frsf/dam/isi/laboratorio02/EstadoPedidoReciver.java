package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDetalleDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class EstadoPedidoReciver extends BroadcastReceiver {

    public static final String ESTADO_ACEPTADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";
    public static final String ESTADO_CANCELADO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_CANCELADO";
    public static final String ESTADO_EN_PREPARACION = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_EN_PREPARACION";
    public static final String ESTADO_LISTO = "ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_LISTO";

    private PedidoDao pedidoDao;
    private PedidoDetalleDao pedidoDetalleDao;

    @Override
    public void onReceive(final Context context, Intent intent) {
        pedidoDao = MyDb.getInstance(context).getPedidoDao();
        pedidoDetalleDao = MyDb.getInstance(context).getPedidoDetalleDao();
        String estado="DESCONOCIDO";
        if (intent.getAction() != null) {
            switch (intent.getAction()){
                case ESTADO_ACEPTADO:
                    estado="ACEPTADO";
                    break;
                case ESTADO_CANCELADO:
                    estado="CANCELADO";
                    break;
                case ESTADO_EN_PREPARACION:
                    estado="EN_PREPARACIÓN";
                    break;
                case ESTADO_LISTO:
                    estado="LISTO";
                    break;
            }
        }
        Bundle extras = intent.getExtras();
        if (extras != null){
            PedidoRepository pr = new PedidoRepository();
            //Pedido p = pr.buscarPorId((int)extras.get("idPedido"));
            final long idPedido = (long)extras.get("idPedido");
            final String estadoPedido = estado;
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                   Pedido p = pedidoDao.getPedido(idPedido);
                   List<PedidoConDetalles> listPd = pedidoDao.buscarPedidoConDetallePorId(p.getId());
                   p.setDetalle(listPd.get(0).detalles);

                   createNotification(p,estadoPedido,context);
                }
            });
            /*DEPRECATED
            * Toast.makeText(context,"Pedido para "+p.getMailContacto()+" ha cambiado de estado a "+estado,Toast.LENGTH_LONG).show();
            * */
            /*Se define el Pending Intent para lanzar la actividad que muestre el historial de pedidos al seleccionar la notificación*/
           /* Intent i = new Intent(context, historialPedidos.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

            *//*Se construye la notificación*//*
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Tu pedido esta "+estado)
                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine("El costo será de $"+p.total())
                            .addLine("Previsto el envio para "+p.getFecha().getHours()+":"+p.getFecha().getMinutes()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            *//*Se muestra la notificación*//*
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(99,mBuilder.build());*/
        }


        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createNotification(Pedido p, String estado,Context context){
        Intent i = new Intent(context, historialPedidos.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        /*Se construye la notificación*/
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tu pedido esta "+estado)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("El costo será de $"+p.total())
                        .addLine("Previsto el envio para "+p.getFecha().getHours()+":"+p.getFecha().getMinutes()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        /*Se muestra la notificación*/
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(99,mBuilder.build());
    }
}
