package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoConDetalles;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM Pedidos")
    List<Pedido> getAll();

    @Query("SELECT * FROM Pedidos WHERE id = :identificador")
    Pedido getPedido(long identificador);

    @Query("SELECT * FROM Pedidos WHERE id = :idPedido")
    List<PedidoConDetalles> buscarPedidoConDetallePorId(long idPedido);

    @Insert
    long insert(Pedido pedido);

    @Delete
    void delete(Pedido pedido);

    @Update
    void update(Pedido pedido);

}
