package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class historialPedidos extends AppCompatActivity {


    private ListView listaPedidos;
    private PedidoRepository repositorio;
    private pedidoAdapter adapterPedido;

    public Button btnHistorialMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        final Context context = historialPedidos.this;
        repositorio = new PedidoRepository();

        btnHistorialMenu = findViewById(R.id.btnHistorialMenu);
        listaPedidos = findViewById(R.id.lstHistorialPedidos);

        adapterPedido = new pedidoAdapter(context, repositorio.getLista());

        listaPedidos.setAdapter(adapterPedido);

        /*listaPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterPedido.clear();
                adapterPedido.addAll(repositorio.getLista());
                adapterPedido.notifyDataSetChanged();
            }
        });*/

        btnHistorialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
