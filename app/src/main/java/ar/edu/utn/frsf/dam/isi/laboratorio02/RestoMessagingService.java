package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class RestoMessagingService extends FirebaseMessagingService {

    public static final String _ID = "ID_PEDIDO";
    public static final String _ESTADO = "NUEVO_ESTADO";

    public RestoMessagingService() {
    }

    @Override
    public  void onMessageReceived(RemoteMessage remoteMessage){
        Map<String, String> param = remoteMessage.getData();
        JSONObject object = new JSONObject(param);
        int id = -1;
        String uriEstado=null;
        Pedido.Estado estado=null;
        try {
            id = Integer.parseInt(object.get(_ID).toString());
            switch (String.valueOf(object.get(_ESTADO))){
                case "ACEPTADO":
                    uriEstado=EstadoPedidoReciver.ESTADO_ACEPTADO;
                    estado = Pedido.Estado.ACEPTADO;
                    break;
                case "CANCELADO":
                    uriEstado=EstadoPedidoReciver.ESTADO_CANCELADO;
                    estado = Pedido.Estado.CANCELADO;
                    break;
                case "EN PREPARACIÓN":
                    uriEstado=EstadoPedidoReciver.ESTADO_EN_PREPARACION;
                    estado = Pedido.Estado.EN_PREPARACION;
                    break;
                case "LISTO":
                    uriEstado=EstadoPedidoReciver.ESTADO_LISTO;
                    estado = Pedido.Estado.LISTO;
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (id != -1 && estado != null) {

            PedidoRepository pr = new PedidoRepository();
            Pedido p = pr.buscarPorId(id);
            p.setEstado(estado);

            sendNotification(uriEstado,id);
        }




    }

    private void sendNotification(String estado, int id){
        Intent i = new Intent(RestoMessagingService.this, EstadoPedidoReciver.class);
        i.setAction(estado);
        i.putExtra("idPedido",id);
        sendBroadcast(i);
    }
}
