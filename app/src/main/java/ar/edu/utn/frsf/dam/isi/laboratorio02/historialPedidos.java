package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class historialPedidos extends AppCompatActivity {


    private ListView listaPedidos;
    private PedidoRepository repositorio;
    private pedidoAdapter adapterPedido;

    public Button btnHistorialMenu;
    public Button btnHistorialNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        final Context context = historialPedidos.this;
        repositorio = new PedidoRepository();

        btnHistorialMenu = findViewById(R.id.btnHistorialMenu);
        btnHistorialNuevo = findViewById(R.id.btnHistorialNuevo);
        listaPedidos = findViewById(R.id.lstHistorialPedidos);

        adapterPedido = new pedidoAdapter(context, repositorio.getLista());

        listaPedidos.setAdapter(adapterPedido);

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
}
