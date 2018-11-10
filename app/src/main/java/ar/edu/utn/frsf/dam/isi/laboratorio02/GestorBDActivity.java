package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.MyDb;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class GestorBDActivity extends AppCompatActivity {

    private Button btnBorrarTodo;
    private Button btnConsultarCategorias;
    private Button btnConsultarProductos;
    private TextView tvResultado;
    private CategoriaDao catDao;
    private ProductoDao proDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestror_bd);
        //Toolbar myToolbar = findViewById(R.id.miBarraHerramientas);
        //setSupportActionBar(myToolbar);

        tvResultado = (TextView) findViewById(R.id.txtResultado);

        proDao= MyDb.getInstance(this).getProductoDao();
        catDao=MyDb.getInstance(this).getCategoriaDao();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opciones, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()){

        case R.id.mnuConsultarCategorias:
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    final String res= consultarCategorias();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultado.setText(res);
                        }
                    });

                }
            };
            Thread t1 = new Thread(r1);
            t1.start();
            return true;

        case R.id.mnuConsultarProductos:
            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    final String res = consultarProductos();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultado.setText(res);
                        }
                    });
                }
            };
            Thread t2 = new Thread(r2);
            t2.start();
            return true;


        case R.id.mnuBorrarTodo:
            Runnable r3 = new Runnable() {
                @Override
                public void run() {
                    borrarTodo();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResultado.setText("");
                        }
                    });

                }
            };
            Thread t3= new Thread(r3);
            t3.start();


        default:

            return super.onOptionsItemSelected(item);

    }

    }




private String consultarCategorias(){

    List<Categoria> lista = catDao.getAll();
    final StringBuilder resultado = new StringBuilder(" === CATEGORIAS ==="+ "\r\n");
    for (Categoria d : lista) {
        resultado.append(d.getId() + ": " + d.getNombre() + "\r\n");
    }
    return resultado.toString();
}
private String consultarProductos(){
        List<Producto> lista = proDao.getAll();

    final StringBuilder resultado = new StringBuilder(" === PRODUCTOS ==="+ "\r\n");
        for(Producto d : lista){
            resultado.append(d.getId() + ": " + d.getNombre() + "\r\n");
        }
        return resultado.toString();
}


    private void borrarTodo() {
        MyDb.getInstance(this).borrarTodo();
    }


}
