package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
    private RadioButton optPedidoRetira;
    private RadioButton optPedidoEnviar;
    private EditText direccion;
    private EditText edtPedidoHoraEntrega;
    private EditText direccionCorreo;
    private ListView lstPedidoItems;
    private Button btnPedidoAddProducto;
    private Button btnPedidoQuitarProducto;
    private Button btnPedidoHacerPedido;
    private Button btnPedidoVolver;
    private TextView lblTotalPedido;
    private detallePedidoAdapter adaptador;
    private ListView listaDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        /*Se inicializan las variables*/

        repositorioPedido= new PedidoRepository();
        repositorioProducto= new ProductoRepository();
        radiogrupo= (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        direccion=(EditText) findViewById(R.id.edtPedidoDireccion);

        //inicializacion de variables para los otros widgets
        direccionCorreo=(EditText) findViewById(R.id.edtPedidoCorreo);
        optPedidoRetira = (RadioButton) findViewById(R.id.optPedidoRetira);
        optPedidoEnviar = (RadioButton) findViewById(R.id.optPedidoEnviar);
        edtPedidoHoraEntrega = (EditText) findViewById(R.id.edtPedidoHoraEntrega);
        //lstPedidoItems = (ListView) findViewById(R.id.lstPedidoItems);  lo comente para probar
        btnPedidoAddProducto = (Button) findViewById(R.id.btnPedidoAddProducto);
        btnPedidoQuitarProducto = (Button) findViewById(R.id.btnPedidoQuitarProducto);
        lblTotalPedido = (TextView) findViewById(R.id.lblTotalPedido);
        btnPedidoHacerPedido = (Button) findViewById(R.id.btnPedidoHacerPedido);
        btnPedidoVolver = (Button) findViewById(R.id.btnPedidoVolver);
        listaDetalles= (ListView) findViewById(R.id.lst_detalles);

        //Este if/else es por si se llama desde el menu principal
        // (origen =0) o desde la ventana historial pedidos (origen =1),
        // si es el ultimo caso entonces debe completar la ventana con los datos del pedido que viene con el intent.
        final Intent intentExtras = getIntent();

        if (intentExtras.getExtras().getInt("origen")==1){
            unPedido=repositorioPedido.buscarPorId(intentExtras.getExtras().getInt("idPedido"));

            //seteo de campos de acuerdo al pedido

            direccionCorreo.setText(unPedido.getMailContacto());
            if (unPedido.getRetirar())
                optPedidoRetira.setChecked(true);
            else
                optPedidoEnviar.setChecked(true);
            direccion.setText(unPedido.getDireccionEnvio());

            //edtPedidoHoraEntrega.setText(unPedido.getFecha().toString()); //TODO:NO anda esto

            //lstPedidoItems    //TODO: setear esta lista con el adaptador

            double costoTotal=0;
            int temp,cantTotal=0;
            for (PedidoDetalle pd: unPedido.getDetalle()) {
                temp= pd.getCantidad();
                costoTotal = costoTotal + (pd.getProducto().getPrecio())*(temp);
                cantTotal = cantTotal + temp; }
            lblTotalPedido.setText(getString(R.string.totalPedido)+String.valueOf(cantTotal));
        }
        else{
            unPedido= new Pedido();
        }

        // radiogrupo= (RadioGroup) findViewById(R.id.radioGroup); lo comente par probar
        // direccion=(EditText) findViewById(R.id.editText2); lo comente para probar

        radiogrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.optPedidoEnviar){
                    direccion.setEnabled(false);
                }
                else{
                    direccion.setEnabled(true);
                }
            }
        });



        detallePedidoAdapter adaptador= new detallePedidoAdapter(getApplicationContext(),unPedido.getDetalle());

        listaDetalles.setAdapter(adaptador);



        // boton para agregar productos al pedido

        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NuevoPedido.this, lista_prod.class);
// me falta referenciar y tomar el producto que selecciona, luego agregarlo a la lista del detalle y visualisarlo



            }
        });


    }
}
