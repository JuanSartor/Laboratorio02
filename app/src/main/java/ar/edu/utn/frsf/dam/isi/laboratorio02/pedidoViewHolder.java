package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class pedidoViewHolder {

    public TextView txtMail;
    public TextView txtFechayHora;
    public TextView txtCantidad;
    public TextView txtCosto;
    public TextView txtEstado;
    public ImageView imagenRetira;
    public Button btnCancelarPedido;
    public LinearLayout layoutAgrupador;


    public pedidoViewHolder(View base){
        this.txtMail = (TextView) base.findViewById(R.id.txtMail);
        this.txtFechayHora = (TextView) base.findViewById(R.id.txtFechayHora);
        this.txtCantidad = (TextView) base.findViewById(R.id.txtCantidad);
        this.txtCosto = (TextView) base.findViewById(R.id.txtCosto);
        this.txtEstado = (TextView) base.findViewById(R.id.txtEstado);
        this.imagenRetira = (ImageView) base.findViewById(R.id.imagenRetira);
        this.btnCancelarPedido = (Button) base.findViewById(R.id.btnCancelarPedido);
        this.layoutAgrupador = (LinearLayout) base.findViewById(R.id.layoutAgrupador);
    }
}

