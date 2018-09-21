package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prod);

        /*Se inicializan las variables*/
        final Context context = this;
        repositorio = new ProductoRepository();
        spinnerCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,repositorio.getCategorias());
        listaProductos = (ListView) findViewById(R.id.lstProductos);

        /*Se definen parametros de preferencia para el spinner*/
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterProducto = new productoAdapter(context, repositorio.buscarPorCategoria((Categoria) (adapterView.getItemAtPosition(i))));
                listaProductos.setAdapter(adapterProducto);
                listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapterProducto.setSelectedIndex(i);
                        adapterProducto.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
