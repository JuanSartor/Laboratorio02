package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class lista_prod extends AppCompatActivity {
    /*Se definen las variables a utilizar*/
    private Spinner spinnerCategoria;
    private ArrayAdapter<Categoria> adapterCategoria;
    private productoAdapter adapterProducto;
    private ListView listaProductos;
    private ProductoRepository repositorio;
    private Button btnProdAddPedido;
    private EditText edtProdCant;
    private CategoriaDao catDao;
    private ProductoDao proDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prod);

        /*Se inicializan las variables*/
        final Intent intentExtras = getIntent();
        btnProdAddPedido = (Button) findViewById(R.id.btnProdAddPedido);
        edtProdCant = (EditText) findViewById(R.id.edtProdCantidad);

        catDao= MyDb.getInstance(this).getCategoriaDao();
       proDao= MyDb.getInstance(this).getProductoDao();

        //Habilitar o deshabilitar boton y campo cantidad de acuerdo a punto de llamada
        if (intentExtras.getExtras().getInt("NUEVO_PEDIDO") == 0) {
            btnProdAddPedido.setEnabled(false);
            edtProdCant.setEnabled(false);
        } else {
            btnProdAddPedido.setEnabled(true);
            edtProdCant.setEnabled(true);
        }

        final Context context = this;
        repositorio = new ProductoRepository();

        Runnable codeHilo = new Runnable() {    //agregado para ejecutar en hilo aparte la carga de combobox y lista
            @Override
            public void run() {
                //CategoriaRest cr = new CategoriaRest();
               // final List<Categoria> listaCategorias = cr.listarTodas();     //obteniendo lista categorias de servidor
                    final List<Categoria> listaCategorias= catDao.getAll();


                runOnUiThread(new Runnable() {      //para ejecutar lo relativo a interfaz en hilo principal (de UI)
                    @Override
                    public void run() {
                        spinnerCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);

                        //version antigua usando repositorio en app
                        //adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,repositorio.getCategorias());

                        adapterCategoria = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listaCategorias);

                        listaProductos = (ListView) findViewById(R.id.lstProductos);

                        /*Se definen parametros de preferencia para el spinner*/
                        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoria.setAdapter(adapterCategoria);

                        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {


                                List<Producto> listaaa = proDao.loadProdByCat(((Categoria) (adapterView.getItemAtPosition(i))).getId());

                                if(listaaa.size()>0 ){

                                    Toast mensaje = Toast.makeText(getApplicationContext(),
                                            "Operacionasdsadsadsadsdsalziada exitosamente!", Toast.LENGTH_SHORT);
                                    mensaje.show();

                                }

                                //adapterProducto = new productoAdapter(context,

                                    //  proDao.loadProdByCat(((Categoria) (adapterView.getItemAtPosition(i))).getId()));
                                listaProductos.setAdapter(adapterProducto);
                                listaProductos.setOnItemClickListener(
                                        new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                adapterProducto.setSelectedIndex(i);
                                                adapterProducto.notifyDataSetChanged();
                                            }
                                        }
                                );

                            }



                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });

                        btnProdAddPedido.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Integer.parseInt(edtProdCant.getText().toString()) > 0) {
                                    Intent salida = new Intent();
                                    salida.putExtra("cantidad", Integer.parseInt(edtProdCant.getText().toString()));
                                    salida.putExtra("idProducto", adapterProducto.getProductoSeleccionado().getId());
                                    setResult(Activity.RESULT_OK, salida);
                                    finish();
                                } else {
                                    Toast mensaje = Toast.makeText(getApplicationContext(),
                                            "No se puede pedir 0 unidades!", Toast.LENGTH_SHORT);
                                    mensaje.show();
                                }
                            }

                        });
                    }});
        }};
        Thread hiloCombos = new Thread(codeHilo);
        hiloCombos.start();
    }

}