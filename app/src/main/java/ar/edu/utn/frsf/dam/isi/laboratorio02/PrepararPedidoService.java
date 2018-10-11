package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {

    private List<Pedido> listaPedidos;

    public PrepararPedidoService () {
        super("ServicioIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(20000);
            PedidoRepository pRep = new PedidoRepository();
            listaPedidos = pRep.getLista();
            for (Pedido ped: listaPedidos)
                if (ped.getEstado()==Pedido.Estado.ACEPTADO)
                    ped.setEstado(Pedido.Estado.EN_PREPARACION);

            Intent i = new Intent();
            i.setAction(EstadoPedidoReciver.ESTADO_EN_PREPARACION);
            sendBroadcast(i);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
