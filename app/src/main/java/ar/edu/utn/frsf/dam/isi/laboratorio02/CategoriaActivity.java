package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private EditText nombreCat;
    private Button btnCrear;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        nombreCat = (EditText) findViewById(R.id.txtNombreCategoria);

        btnCrear = (Button) findViewById(R.id.btnCrearCategoria);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // completar el codigo en el paso “g”
                final CategoriaRest cr = new CategoriaRest();
                Runnable r=new Runnable() {
                    @Override
                    public void run() {
                        Categoria cat= new Categoria(nombreCat.getText().toString());
                        cr.crearCategoria(cat);
                        Runnable r2=new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CategoriaActivity.this,
                                        "Categoria creada!",
                                        Toast.LENGTH_LONG).show();
                                nombreCat.setText("");
                            }
                        };
                        nombreCat.post(r2);
                    }
                };
                Thread segundoHilo = new Thread(r);
                segundoHilo.start();
            }
        });

        btnMenu= (Button) findViewById(R.id.btnCategoriaVolver);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoriaActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
