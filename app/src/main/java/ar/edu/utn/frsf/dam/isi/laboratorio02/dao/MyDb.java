package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MyDb {

    private static MyDb _INSTANCIA_UNICA=null;


    public static MyDb getInstance(Context ctx){
        if(_INSTANCIA_UNICA==null) _INSTANCIA_UNICA = new MyDb(ctx);
        return _INSTANCIA_UNICA;
    }


    private CategoriaDao daocategoria;
    private PedidoDao pedidoDao;
    private PedidoDetalleDao pedidoDetalleDao;
    private  AppBaseDatos db;



    private MyDb(Context ctx){
        db = Room.databaseBuilder(ctx,
                AppBaseDatos.class, "base-laboratorio")
                .fallbackToDestructiveMigration()
                .build();
        daocategoria = db.categoriaDao();
        pedidoDao = db.pedidoDao();
        pedidoDetalleDao = db.pedidoDetalleDao();

    }


    public void borrarTodo(){
        this.db.clearAllTables();
    }

    public CategoriaDao getCategoriaDao() {
        return daocategoria;
    }

    public void setCategoriaDao(CategoriaDao catDao) {
        this.daocategoria = catDao;
    }

    public PedidoDao getPedidoDao() {
        return pedidoDao;
    }

    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public PedidoDetalleDao getPedidoDetalleDao() {
        return pedidoDetalleDao;
    }

    public void setPedidoDetalleDao(PedidoDetalleDao pedidoDetalleDao) {
        this.pedidoDetalleDao = pedidoDetalleDao;
    }
}
