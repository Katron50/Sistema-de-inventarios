package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Compra;
import java.util.List;


public interface CompraRepository extends JpaRepository<Compra, Long>{
    // 

    List<Compra> findByDisponibility(boolean disponibility);
}
