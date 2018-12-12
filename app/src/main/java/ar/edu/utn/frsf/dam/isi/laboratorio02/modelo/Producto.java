package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "Productos",foreignKeys = @ForeignKey(entity = Categoria.class,
parentColumns = "id",
childColumns = "cate_id",
onDelete = ForeignKey.CASCADE))
public class Producto {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @NonNull
    @ColumnInfo(name = "precio")
    private Double precio;

   @Embedded(prefix = "cat_")
    private Categoria categoria;

   @ColumnInfo(name="cate_id")
   private Integer catid;

    public Producto() {
    }

    @Ignore
    public Producto(String nombre, String descripcion, Double precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    @Ignore
    public Producto(String nombre, Double precio, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    @Ignore
    public Producto(Integer id, String nombre, String descripcion, Double precio, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return nombre+ "( $" + precio +")";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
