package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Producto;
import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByIdAndDisponibility (long id, boolean disponibility);

    List<Producto> findByDisponibility(boolean disponibility);

    int countByDisponibilityAndCategoriaId(boolean disponibility, Long categoriaId);
} 
