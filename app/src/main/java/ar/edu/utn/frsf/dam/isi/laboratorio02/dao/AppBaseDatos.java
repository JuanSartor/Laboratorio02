package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;


@Database(entities = {Categoria.class}, version = 1)

public abstract class AppBaseDatos extends RoomDatabase {


    public abstract CategoriaDao categoriaDao();    
}
