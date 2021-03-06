package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;

public class historialPedidos extends AppCompatActivity {


    private ListView listaPedidos;
    //private PedidoRepository repositorio;
    private PedidoDao pedidoDao;
    private pedidoAdapter adapterPedido;

    public Button btnHistorialMenu;
    public Button btnHistorialNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        final Context context = historialPedidos.this;
        //repositorio = new PedidoRepository();
        pedidoDao = MyDb.getInstance(context).getPedidoDao();

        btnHistorialMenu = findViewById(R.id.btnHistorialMenu);
        btnHistorialNuevo = findViewById(R.id.btnHistorialNuevo);
        listaPedidos = findViewById(R.id.lstHistorialPedidos);

        //adapterPedido = new pedidoAdapter(context, repositorio.getLista());

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Pedido> lista = getAllPedidos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterPedido = new pedidoAdapter(context, lista);
                        listaPedidos.setAdapter(adapterPedido);
                    }
                });
            }
        });

        //adapterPedido = new pedidoAdapter(context, pedidoDao.getAll());
        //listaPedidos.setAdapter(adapterPedido);

        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(historialPedidos.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnHistorialNuevo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(historialPedidos.this, NuevoPedido.class);
                i.putExtra("origen",0);
                startActivityForResult(i,0);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                adapterPedido.notifyDataSetChanged();
    }

    private List<Pedido> getAllPedidos(){
        List<Pedido> lista = pedidoDao.getAll();
        for (Pedido pedido : lista){
            List<PedidoConDetalles> pedidoConDetalles = pedidoDao.buscarPedidoConDetallePorId(pedido.getId());
            pedido.setDetalle(pedidoConDetalles.get(0).detalles);
        }
        return lista;
    }
}
