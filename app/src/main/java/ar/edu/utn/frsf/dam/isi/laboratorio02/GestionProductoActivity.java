package ar.edu.utn.frsf.dam.isi.laboratorio02;

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

import java.util.ArrayList;
import java.util.List;

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

        Runnable nuevoHilo = new Runnable() {
            @Override
            public void run() {



       CategoriaRest cr= new CategoriaRest();
        final List<Categoria> listacategorias= cr.listarTodas();



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
            @Override public void onClick(View v){
                final Producto nuevo_producto = new Producto();
                ProductoRetrofit clienteRest = RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);


                if(flagActualizacion){
                    int id=Integer.valueOf(idProductoBuscar.getText().toString());
                     final Call<Producto> altaCall= clienteRest.actualizarProducto(id,nuevo_producto);


                    if((nombreProducto.getText()!=null)&&(descProducto.getText()!=null)&&(precioProducto.getText()!=null&&(cat_seleccionada!=null))){

                        nuevo_producto.setNombre(nombreProducto.getText().toString());
                        nuevo_producto.setDescripcion(descProducto.getText().toString());
                        nuevo_producto.setPrecio(Double.valueOf(precioProducto.getText().toString()));
                        nuevo_producto.setCategoria(cat_seleccionada);




                        altaCall.enqueue(new Callback<Producto>() {
                            @Override
                            public void onResponse(Call<Producto> call, Response<Producto> response) {

                                if(response.isSuccessful()){

                                    Toast mensaje = Toast.makeText(getApplicationContext(),
                                            "Operacion realziada exitosamente!", Toast.LENGTH_SHORT);
                                    mensaje.show();


                                }
                                else{}

                            }

                            @Override
                            public void onFailure(Call<Producto> call, Throwable t) {

                            }
                        });



                    }
                    else{

                        Toast mensaje = Toast.makeText(getApplicationContext(),
                                "Por favor, complete todos los campos!", Toast.LENGTH_SHORT);
                        mensaje.show();

                    }


                }
                else{
                final Call<Producto> altaCall= clienteRest.crearProducto(nuevo_producto);


                    if((nombreProducto.getText()!=null)&&(descProducto.getText()!=null)&&(precioProducto.getText()!=null&&(cat_seleccionada!=null))){

                        nuevo_producto.setNombre(nombreProducto.getText().toString());
                        nuevo_producto.setDescripcion(descProducto.getText().toString());
                        nuevo_producto.setPrecio(Double.valueOf(precioProducto.getText().toString()));
                        nuevo_producto.setCategoria(cat_seleccionada);




                        altaCall.enqueue(new Callback<Producto>() {
                            @Override
                            public void onResponse(Call<Producto> call, Response<Producto> response) {

                                if(response.isSuccessful()){

                                    Toast mensaje = Toast.makeText(getApplicationContext(),
                                            "Operacion realziada exitosamente!", Toast.LENGTH_SHORT);
                                    mensaje.show();


                                }
                                else{}

                            }

                            @Override
                            public void onFailure(Call<Producto> call, Throwable t) {

                            }
                        });



                    }
                    else{

                        Toast mensaje = Toast.makeText(getApplicationContext(),
                                "Por favor, complete todos los campos!", Toast.LENGTH_SHORT);
                        mensaje.show();

                    }


                }







                Intent i= new Intent(GestionProductoActivity.this, MainActivity.class);
                startActivity(i);

            }
        });




   btnBorrar.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           int id=Integer.valueOf(idProductoBuscar.getText().toString());


           ProductoRetrofit clienteRest = RestClient.getInstance()
                   .getRetrofit()
                   .create(ProductoRetrofit.class);
           final Call<Producto> altaCall= clienteRest.borrar(id);



           altaCall.enqueue(new Callback<Producto>() {
               @Override
               public void onResponse(Call<Producto> call, Response<Producto> response) {

                   if(response.isSuccessful()){
                       Toast mensaje = Toast.makeText(getApplicationContext(),
                               "Se ha eliminado un nuevo producto!", Toast.LENGTH_SHORT);
                       mensaje.show();

                   }

                   else{

                   }

               }

               @Override
               public void onFailure(Call<Producto> call, Throwable t) {

               }
           });

           Intent i= new Intent(GestionProductoActivity.this, MainActivity.class);
           startActivity(i);
       }
   });

   btnBuscar.setOnClickListener(new View.OnClickListener()  {
       @Override
       public void onClick(View v) {

           int id=Integer.valueOf(idProductoBuscar.getText().toString());


           ProductoRetrofit clienteRest = RestClient.getInstance()
                   .getRetrofit()
                   .create(ProductoRetrofit.class);



    final Call<Producto> alta = clienteRest.buscarProductoPorId(id);


           alta.enqueue(new Callback<Producto>() {
               @Override
               public void onResponse(Call<Producto> call, Response<Producto> response) {



                   if(response.isSuccessful()){


                       Producto retorno= (Producto) response.body();

                       nombreProducto.setText(retorno.getNombre());

                       descProducto.setText(retorno.getDescripcion());
                       precioProducto.setText(retorno.getPrecio().toString());



                   }
                   else{
                       try {
                           JSONObject jObjError = new JSONObject(response.errorBody().string());
                           Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                       } catch (Exception e) {
                           Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                       }


                   }

               }

               @Override
               public void onFailure(Call<Producto> call, Throwable t) {



               }



       });
   }



    });





   


}


}