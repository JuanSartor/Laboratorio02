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

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class lista_prod extends AppCompatActivity {
    /*Se definen las variables a utilizar*/
    private Spinner spinnerCategoria;
    private ArrayAdapter<Categoria> adapterCategoria;
    private productoAdapter adapterProducto;
    private ListView listaProductos;
    private ProductoRepository repositorio;
    private Button btnProdAddPedido;
    private EditText edtProdCant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prod);

        /*Se inicializan las variables*/
        final Intent intentExtras = getIntent();
        final Context context = this;
        repositorio = new ProductoRepository();
        btnProdAddPedido = (Button) findViewById(R.id.btnProdAddPedido);
        spinnerCategoria = (Spinner) findViewById(R.id.cmbProductosCategoria);
        edtProdCant = (EditText) findViewById(R.id.edtProdCantidad);
        adapterCategoria = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,repositorio.getCategorias());
        listaProductos = (ListView) findViewById(R.id.lstProductos);

        //Habilitar o deshabilitar boton y campo cantidad de acuerdo a punto de llamada
        if (intentExtras.getExtras().getInt("NUEVO_PEDIDO")==0) {
            btnProdAddPedido.setEnabled(false);
            edtProdCant.setEnabled(false);}
        else {
            btnProdAddPedido.setEnabled(true);
            edtProdCant.setEnabled(true);}

        /*Se definen parametros de preferencia para el spinner*/
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterProducto = new productoAdapter(context,
                        repositorio.buscarPorCategoria((Categoria) (adapterView.getItemAtPosition(i))));
                listaProductos.setAdapter(adapterProducto);
                listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapterProducto.setSelectedIndex(i);
                        adapterProducto.notifyDataSetChanged();
                    }
                }
                );
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnProdAddPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salida = new Intent();
                salida.putExtra("cantidad", edtProdCant.getText());
                salida.putExtra("idProducto",listaProductos.getSelectedItemId());
                setResult(Activity.RESULT_OK, salida);
                finish();
            }

        });
    }

}