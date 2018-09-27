package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,NuevoPedido.class);
                i.putExtra("origen",0);
                startActivity(i);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, historialPedidos.class);
                PedidoRepository rep=new PedidoRepository();

                //DATOS DE PRUEBA///////////////////////////////////////////// //TODO: quitar estos datos de prueba
                //productos de prueba para armar "pedidos detalle"
                Producto p1 = new Producto("perro",14.3,new Categoria("perros"));
                Producto p2 = new Producto("carpincho",107.3,new Categoria("fauna"));
                Producto p3 = new Producto("tierra",14.3,new Categoria("comida"));
                //"pedidos detalle" de prueba para armar listas de "pedidos detalle"
                PedidoDetalle pedDet= new PedidoDetalle(4,p1);
                PedidoDetalle pedDet2= new PedidoDetalle(10,p2);
                PedidoDetalle pedDet3= new PedidoDetalle(10,p3);
                PedidoDetalle pedDet4= new PedidoDetalle(10,p1);
                //lista de "pedidos detalle" de prueba para armar pedidos
                List<PedidoDetalle> listaPedDet = new ArrayList<>();
                List<PedidoDetalle> listaPedDet2 = new ArrayList<>();
                listaPedDet.add(pedDet);
                listaPedDet.add(pedDet2);
                listaPedDet.add(pedDet4);
                listaPedDet2.add(pedDet3);
                //pedidos de prueba, el try/catch es por la fecha, lo exige aunque no fallaria nunca
                try {
                    Date dTime = new SimpleDateFormat("HH:mm").parse("13:10");
                    rep.guardarPedido(new Pedido(dTime,listaPedDet,Pedido.Estado.RECHAZADO,null,"david_h.94@hotmail.com",true));
                    rep.guardarPedido(new Pedido(dTime,listaPedDet,Pedido.Estado.ACEPTADO,"el molino 1257","david_h.94@hotmail.com",false));
                    rep.guardarPedido(new Pedido(dTime,listaPedDet2,Pedido.Estado.EN_PREPARACION,null,"david.harispe@gmail.com",true));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //////////////////////////////////////////////////////////////

                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, lista_prod.class);
                i.putExtra("NUEVO_PEDIDO",0);
                startActivityForResult(i,0);
            }
        });
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode== Activity.RESULT_OK){
            if(requestCode==1){
                int cantidad = Integer.valueOf(data.getExtras().getString("cantidad"));
                int idProducto = Integer.valueOf(data.getExtras().getString("idProducto"));
            }}
    }*/
}
