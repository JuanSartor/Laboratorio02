package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pedidoViewHolder {
    TextView texto1;
    TextView texto2;
    Button btnCancelar;

    public pedidoViewHolder(View base){
        this.texto1 = (TextView) base.findViewById(R.id.texto1);
        this.btnCancelar = (Button) base.findViewById(R.id.btnCancelar);
    }
}

