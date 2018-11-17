package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

public class NuevoPedido extends AppCompatActivity {

    /*Se definen las variables a utilizar*/
    private Pedido unPedido;
   // private PedidoRepository repositorioPedido;
    private PedidoDao pedidoDao;
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
        //repositorioPedido= new PedidoRepository();
        pedidoDao = MyDb.getInstance(this).getPedidoDao();
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
            //unPedido=repositorioPedido.buscarPorId(intentExtras.getInt("idPedido"));
            unPedido = pedidoDao.getPedido(intentExtras.getInt("idPedido"));

            //seteo de campos de acuerdo al pedido

            direccionCorreo.setText(unPedido.getMailContacto());
            if (unPedido.getRetirar())
                optPedidoRetira.setChecked(true);
            else
                optPedidoEnviar.setChecked(true);
            direccion.setText(unPedido.getDireccionEnvio());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            edtPedidoHoraEntrega.setText(sdf.format(unPedido.getFecha()));

            lblTotalPedido.setText(getString(R.string.totalPedido)+String.valueOf(unPedido.total()));

            btnPedidoAddProducto.setEnabled(false);     //se deshabilitan estos botones porque el enunciado no dice
            btnPedidoQuitarProducto.setEnabled(false);  // nada de que se puedan modificar los pedidos desde el
            btnPedidoHacerPedido.setEnabled(false);     // historial, se debe cancelar el pedido y hacer otro.
        }
        else{

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            if (prefs.getBoolean("keyRetiro",false)){
                optPedidoRetira.setChecked(true);
            }else{
                optPedidoEnviar.setChecked(true);
            }
            direccionCorreo.setText(prefs.getString("keyCorreo"," "));
            unPedido= new Pedido();}

        adaptador= new detallePedidoAdapter(getApplicationContext(),unPedido.getDetalle());
        listaDetalles.setAdapter(adaptador);

        radiogrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.optPedidoEnviar){
                    direccion.setEnabled(true);}
                else{
                    direccion.setEnabled(false);}}
        });

        btnPedidoQuitarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unPedido.getDetalle().size()>0){
                unPedido.quitarDetalle(adaptador.getSeleccionado());
                lblTotalPedido.setText(getString(R.string.totalPedido)+String.valueOf(unPedido.total()));
                adaptador.notifyDataSetChanged();}
            }
        });

        btnPedidoHacerPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                Runnable nuevoRun = new Runnable() {
                        @Override
                        public void run() {
                        try {
                            Thread.currentThread().sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}

                        // buscar pedidos no aceptados y aceptarlos utomáticamente
                        //List<Pedido> lista = repositorioPedido.getLista();
                        List<Pedido> lista = pedidoDao.getAll();
                        for(Pedido p:lista){
                            if(p.getEstado().equals(Pedido.Estado.REALIZADO))
                                p.setEstado(Pedido.Estado.ACEPTADO);

                            Intent i = new Intent(NuevoPedido.this, EstadoPedidoReciver.class);
                            i.setAction(EstadoPedidoReciver.ESTADO_ACEPTADO);
                            i.putExtra("idPedido",p.getId());
                            sendBroadcast(i);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Toast.makeText(NuevoPedido.this,
                                        "Informacion de pedidos actualizada!",
                                        Toast.LENGTH_LONG).show();*/
                            }
                        });
                    }
                };

                Thread unHilo = new Thread(nuevoRun);
                unHilo.start();

                String[] horaIngresada = edtPedidoHoraEntrega.getText().toString().split(":");
                GregorianCalendar horas = new GregorianCalendar();
                int valorHora = Integer.valueOf(horaIngresada[0]);
                int valorMinuto = Integer.valueOf(horaIngresada[1]);
                
                if(valorHora<0 || valorHora>23){
                    Toast.makeText(NuevoPedido.this,
                            "La hora ingresada ("+valorHora+") es incorrecta",
                            Toast.LENGTH_LONG).show();
                    return;}
                if(valorMinuto <0 || valorMinuto >59){
                    Toast.makeText(NuevoPedido.this,
                            "Los minutos ingresados ("+valorMinuto+") son incorrectos",
                            Toast.LENGTH_LONG).show();
                    return;}

                if (direccionCorreo.getText()!=null && (optPedidoRetira.isChecked() || optPedidoEnviar.isChecked())
                        && direccion.getText()!=null && edtPedidoHoraEntrega.getText()!=null && unPedido.getDetalle().size()>0){

                    unPedido.setEstado(Pedido.Estado.REALIZADO);
                    unPedido.setDetalle(adaptador.getDetallePedido()); //le pide el detalle de lo que muestra actualmente el adaptador
                    unPedido.setDireccionEnvio(direccion.getText().toString());
                    unPedido.setMailContacto(direccionCorreo.getText().toString());

                    horas.set(Calendar.HOUR_OF_DAY,valorHora);
                    horas.set(Calendar.MINUTE,valorMinuto);
                    horas.set(Calendar.SECOND,Integer.valueOf(0));
                    unPedido.setFecha(horas.getTime());

                    if(optPedidoEnviar.isChecked())
                        unPedido.setRetirar(false);
                    else
                        unPedido.setRetirar(true);
                    //repositorioPedido.guardarPedido(unPedido);  //esto lo agrega al repositorio y le setea el id
                    pedidoDao.insertAll(unPedido); //esto lo agrega al repositorio y le setea el id
                    Log.d("ID_PEDIDO",String.valueOf(unPedido.getId()));
                    Intent i = new Intent(NuevoPedido.this, historialPedidos.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast mensaje = Toast.makeText(getApplicationContext(),
                            "Por favor, complete todos los campos!", Toast.LENGTH_SHORT);
                    mensaje.show();
                }
            }
        });

        btnPedidoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}
        });

        btnPedidoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, lista_prod.class);
                i.putExtra("NUEVO_PEDIDO",1);
                startActivityForResult(i,1);}
        });}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode== Activity.RESULT_OK){
            if(requestCode==1){
                int cantidad = Integer.valueOf(data.getExtras().getInt("cantidad"));
                int idProducto = Integer.valueOf(data.getExtras().getInt("idProducto"));

                PedidoDetalle pedDet = new PedidoDetalle(cantidad,repositorioProducto.buscarPorId(idProducto));
                pedDet.setPedido(unPedido);
                adaptador.notifyDataSetChanged();
                lblTotalPedido.setText(getString(R.string.totalPedido)+String.valueOf(unPedido.total()));
            }}
    }
}
