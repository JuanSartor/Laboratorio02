package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;

@Dao
public interface PedidoDetalleDao {
    @Query("SELECT * FROM PedidoDetalle")
    List<PedidoDetalle> getAll();

    @Query("SELECT * FROM PedidoDetalle WHERE ped_id = :id")
    List<PedidoConDetalles> getDetalleFromPedido(long id);

    @Insert
    void insertAll(PedidoDetalle... pedidoDetalle);

    @Delete
    void delete(PedidoDetalle pedidoDetalle);

    @Update
    void update(PedidoDetalle pedidoDetalle);

}
