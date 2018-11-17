package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM Pedidos")
    List<Pedido> getAll();

    @Query("SELECT * FROM Pedidos WHERE PedidoId = :identificador")
    Pedido getPedido(int identificador);

    @Insert
    void insertAll(Pedido... pedido);

    @Delete
    void delete(Pedido pedido);

    @Update
    void update(Pedido pedido);

}
