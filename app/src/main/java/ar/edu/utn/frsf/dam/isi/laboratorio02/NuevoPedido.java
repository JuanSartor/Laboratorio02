package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

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
    private ListView listaDetalles;
    private Button btnPedidoAddProducto;
    private Button btnPedidoQuitarProducto;
    private Button btnPedidoHacerPedido;
    private Button btnPedidoVolver;
    private TextView lblTotalPedido;
    private detallePedidoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        //*Se inicializan las variables*//*
        repositorioPedido= new PedidoRepository();
        repositorioProducto= new ProductoRepository();
        radiogrupo= (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        direccion=(EditText) findViewById(R.id.edtPedidoDireccion);

        //inicializacion de variables para los otros widgets
        direccionCorreo=(EditText) findViewById(R.id.edtPedidoCorreo);
        optPedidoRetira = (RadioButton) findViewById(R.id.optPedidoRetira);
        optPedidoEnviar = (RadioButton) findViewById(R.id.optPedidoEnviar);
        edtPedidoHoraEntrega = (EditText) findViewById(R.id.edtPedidoHoraEntrega);
        btnPedidoAddProducto = (Button) findViewById(R.id.btnPedidoAddProducto);
        btnPedidoQuitarProducto = (Button) findViewById(R.id.btnPedidoQuitarProducto);
        lblTotalPedido = (TextView) findViewById(R.id.lblTotalPedido);
        btnPedidoHacerPedido = (Button) findViewById(R.id.btnPedidoHacerPedido);
        btnPedidoVolver = (Button) findViewById(R.id.btnPedidoVolver);
        listaDetalles= (ListView) findViewById(R.id.lst_detalles);

        final Bundle intentExtras = getIntent().getExtras();
        //Este if/else es por si se llama desde el menu principal
        // o desde la ventana historial pedidos. Si es el ultimo caso,
        // se debe completar la ventana con los datos del pedido que viene con el intent.

        if (intentExtras.getInt("origen")==1){
            unPedido=repositorioPedido.buscarPorId(intentExtras.getInt("idPedido"));

            //seteo de campos de acuerdo al pedido

            direccionCorreo.setText(unPedido.getMailContacto());
            if (unPedido.getRetirar())
                optPedidoRetira.setChecked(true);
            else
                optPedidoEnviar.setChecked(true);
            direccion.setText(unPedido.getDireccionEnvio());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            edtPedidoHoraEntrega.setText(sdf.format(unPedido.getFecha()));

            double costoTotal=0;
            int temp;
            for (PedidoDetalle pd: unPedido.getDetalle()) {
                temp= pd.getCantidad();
                costoTotal = costoTotal + (pd.getProducto().getPrecio())*(temp);}
            lblTotalPedido.setText(getString(R.string.totalPedido)+String.valueOf(costoTotal));

            btnPedidoAddProducto.setEnabled(false);     //se deshabilitan estos botones porque el enunciado no dice
            btnPedidoQuitarProducto.setEnabled(false);  // nada de que se puedan modificar los pedidos desde el
            btnPedidoHacerPedido.setEnabled(false);     // historial, se debe cancelar el pedido y hacer otro.
        }
        else{
            unPedido= new Pedido();}

        final detallePedidoAdapter adaptador= new detallePedidoAdapter(getApplicationContext(),unPedido.getDetalle());
        listaDetalles.setAdapter(adaptador);

        radiogrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.optPedidoEnviar){
                    direccion.setEnabled(false);}
                else{
                    direccion.setEnabled(true);}}
        });

        btnPedidoQuitarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: codigo que quita el producto de la lista (del pedido)
            }
        });

        btnPedidoHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: codigo de armar efectivamente el pedido y agregarlo a la clase repositorio producto.
            }
        });


        btnPedidoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //TODO: no se si esto termina asi nomas, creo que si
            }
        });

        // boton para agregar productos al pedido

        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, lista_prod.class);
                // me falta referenciar y tomar el producto que selecciona, luego agregarlo a la lista del detalle y visualisarlo
                i.putExtra("NUEVO_PEDIDO",1);
                startActivityForResult(i,1);
            }
        });}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode== Activity.RESULT_OK){
            if(requestCode==1){
                int cantidad = Integer.valueOf(data.getExtras().getString("cantidad"));
                int idProducto = Integer.valueOf(data.getExtras().getString("idProducto"));
                //TODO: continuar con lo que se haria con estos datos que se obtienen de la lista de productos, crear el detalle pedido, el pedido, etc.
            }}
    }
}
