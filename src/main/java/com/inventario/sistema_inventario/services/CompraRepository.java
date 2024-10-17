package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.sistema_inventario.models.Compra;
import com.inventario.sistema_inventario.models.Producto;

import java.util.List;


public interface CompraRepository extends JpaRepository<Compra, Long>{
    // 

    List<Compra> findByDisponibility(boolean disponibility);

    List<Compra> findByDisponibilityAndProducto(boolean disponibility, Producto producto);

    //Suma de la cantidad Actual por producto
    @Query("SELECT SUM(c.cantidadActual) FROM Compra c WHERE c.producto.id = :id_producto AND c.disponibility = true")
    Double sumCantidadByProducto(@Param("id_producto") Long id_producto);

}
