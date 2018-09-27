package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class NuevoPedido extends AppCompatActivity {

    /*Se definen las variables a utilizar*/
    private Pedido unPedido;
    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;
    private RadioGroup radiogrupo;
    private EditText direccion;
    private ListView listaDetalles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        /*Se inicializan las variables*/
        unPedido= new Pedido();
        repositorioPedido= new PedidoRepository();
        repositorioProducto= new ProductoRepository();
        radiogrupo= (RadioGroup) findViewById(R.id.radioGroup);
        direccion=(EditText) findViewById(R.id.editText2);
        detallePedidoAdapter adaptador= new detallePedidoAdapter(getApplicationContext(),unPedido.getDetalle());

        radiogrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.radioButton){
                    direccion.setEnabled(false);
                }
                else{
                    direccion.setEnabled(true);
                }
            }
        });


        listaDetalles= (ListView) findViewById(R.id.lst_detalles);

    }
}
