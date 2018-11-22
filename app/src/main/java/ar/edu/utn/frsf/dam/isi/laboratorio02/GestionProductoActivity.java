package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity{

    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;
    private ArrayAdapter<Categoria> comboAdapter;
    private Categoria cat_seleccionada;
    private ProductoDao prodDao;
    private CategoriaDao catDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);




        cat_seleccionada= new Categoria();
        flagActualizacion = false;
        opcionNuevoBusqueda = (ToggleButton) findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = (EditText) findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar= (Button) findViewById(R.id.btnAbmProductoBorrar);
        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);

        final Context context = this;



        nombreProducto.setText("");
        descProducto.setText("");
        precioProducto.setText("");
       catDao= MyDb.getInstance(context).getCategoriaDao();

        prodDao= MyDb.getInstance(context).getProductoDao();



        Runnable nuevoHilo = new Runnable() {
            @Override
            public void run() {


             final List<Categoria> listacategorias= catDao.getAll();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {



        comboAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listacategorias);

        comboAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        comboCategorias.setAdapter(comboAdapter);


        comboCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_seleccionada= (Categoria) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                cat_seleccionada=null;
            }
        });


            }
        });

            }
        };
        Thread hiloCombos = new Thread(nuevoHilo);
        hiloCombos.start();



        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                flagActualizacion =isChecked;
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(isChecked);
                idProductoBuscar.setEnabled(isChecked);



            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(GestionProductoActivity.this, MainActivity.class);
                startActivity(i);
            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



                if(!flagActualizacion){



                                       if((idProductoBuscar.getText().length()!=0)&&(nombreProducto.getText().length()!=0)&&(descProducto.getText().length()!=0)&&(precioProducto.getText().length()!=0)&&(cat_seleccionada.getNombre()!=null)){

                                           int id=Integer.valueOf(idProductoBuscar.getText().toString());
                                           final int [] arr= new int[1];
                                           arr[0]=id;

                                           Runnable r = new Runnable() {
                                               @Override
                                               public void run() {
                                                   final Producto actualizar_producto= prodDao.loadAllByIds(arr).get(0);

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                actualizar_producto.setNombre(nombreProducto.getText().toString());
                                                                actualizar_producto.setDescripcion(descProducto.getText().toString());
                                                                actualizar_producto.setPrecio(Double.valueOf(precioProducto.getText().toString()));
                                                                actualizar_producto.setCategoria(cat_seleccionada);

                                                                Runnable r2 = new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        prodDao.update(actualizar_producto);


                                                                    }
                                                                };

                                                                Thread t1 = new Thread(r2);
                                                                t1.start();
                                                                Toast mensaje = Toast.makeText(getApplicationContext(),
                                                                        "Operacion realziada exitosamente!", Toast.LENGTH_SHORT);
                                                                mensaje.show();

                                                            }
                                                        });

                                               }
                                           };

                                         Thread t= new Thread(r);
                                         t.start();

                                       }
                                       else{

                                           Toast mensaje = Toast.makeText(getApplicationContext(),
                                                   "Por favor, complete todos los campos!", Toast.LENGTH_SHORT);
                                           mensaje.show();

                                       }

                }
                else{



                    if((nombreProducto.getText().length()!=0)&&(descProducto.getText().length()!=0)&&(precioProducto.getText().length()!=0)&&(cat_seleccionada.getNombre()!=null)){

                        final Producto nuevo_producto = new Producto();
                       Runnable runnable = new Runnable() {
                           @Override
                           public void run() {


                        nuevo_producto.setNombre(nombreProducto.getText().toString());
                        nuevo_producto.setDescripcion(descProducto.getText().toString());
                        nuevo_producto.setPrecio(Double.valueOf(precioProducto.getText().toString()));
                       nuevo_producto.setCategoria(cat_seleccionada);


                        prodDao.insertAll(nuevo_producto);


                               Runnable r2 = new Runnable() {
                                   @Override
                                   public void run() {


                       Toast mensaje = Toast.makeText(getApplicationContext(),
                             "Se ha cargado un nuevo producto!", Toast.LENGTH_SHORT);
                        mensaje.show();
                                   }
                               };
                               nombreProducto.post(r2);


                           }
                       };
                       Thread Hilo = new Thread(runnable);
                       Hilo.start();



                    }
                    /**
                     * TEST
                     *
                     * */
                   else{

                        Toast mensaje = Toast.makeText(getApplicationContext(),
                                "Por favor, complete todos los campos!", Toast.LENGTH_SHORT);
                        mensaje.show();

                    }



                }


            }
        });




   btnBorrar.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {



            if((idProductoBuscar.getText().length()!=0)&&(idProductoBuscar.getText()!=null)) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        int id=Integer.valueOf(idProductoBuscar.getText().toString());
                        final int[] arrePar= new int[1];
                        arrePar[0]=id;
                       List<Producto>  lisPro = prodDao.loadAllByIds(arrePar);

                       if(lisPro.size()>0){
                           Producto proAeliminar = lisPro.get(0);
                        prodDao.delete(proAeliminar);
                           Intent i= new Intent(GestionProductoActivity.this, MainActivity.class);
                           startActivity(i);
                       }
                        else{
                           Toast mensaje = Toast.makeText(getApplicationContext(),
                                   "Seleccione un id valido", Toast.LENGTH_SHORT);
                           mensaje.show();

                       }
                    }
                };
                Thread t = new Thread(r);
                t.start();

           Toast mensaje = Toast.makeText(getApplicationContext(),
                   "Se ha eliminado un producto", Toast.LENGTH_SHORT);
           mensaje.show();}
           else{
                Toast mensaje = Toast.makeText(getApplicationContext(),
                        "Seleccione un id", Toast.LENGTH_SHORT);
                mensaje.show();

            }



       }
   });




   btnBuscar.setOnClickListener(new View.OnClickListener()  {
       @Override
       public void onClick(View v) {

          final int [] arregloIds= new int[1];
           if((idProductoBuscar.getText().length()!=0)&&(idProductoBuscar.getText()!=null)) {

               int id=Integer.valueOf(idProductoBuscar.getText().toString());
            arregloIds[0] = id;

    Runnable r = new Runnable() {
        @Override
        public void run() {

            final List<Producto>  retorno =  prodDao.loadAllByIds(arregloIds);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(retorno.size()>0){
                        idProductoBuscar.setEnabled(false);
                    Producto pro= retorno.get(0);
                    nombreProducto.setText(pro.getNombre());
                    descProducto.setText(pro.getDescripcion());
                    precioProducto.setText(pro.getPrecio().toString());
                    comboCategorias.setSelection(comboAdapter.getPosition(pro.getCategoria()));

                }

                    else {
                        Toast mensaje = Toast.makeText(getApplicationContext(),
                                "Seleccione un id valido", Toast.LENGTH_SHORT);
                        mensaje.show();
                    }
                }
            });


        }
    };

    Thread t = new Thread(r);
    t.start();

           }

           else{
               Toast mensaje = Toast.makeText(getApplicationContext(),
                       "Seleccione un id", Toast.LENGTH_SHORT);
               mensaje.show();

           }



   }



    });








}


}
