package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

@Dao
public interface CategoriaDao {
   @Query("SELECT * FROM Categorias")
   List<Categoria> getAll();

    @Query("SELECT * FROM Categorias WHERE id IN (:categoriaIds)")
    List<Categoria> loadAllByIds(int[] categoriaIds);


    @Insert
    void insertAll(Categoria... categoria);

    @Delete
    void delete(Categoria categoria);

    @Update
    void update(Categoria categoria);

}
