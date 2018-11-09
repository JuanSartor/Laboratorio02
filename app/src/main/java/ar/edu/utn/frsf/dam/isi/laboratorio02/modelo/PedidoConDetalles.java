package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

public class PedidoConDetalles {
    @Embedded
    public Pedido pedido;
    //TODO terminar PedidoCOnDetalles
    //@Relation(parentColumn = "id", entityColumn = )
}
