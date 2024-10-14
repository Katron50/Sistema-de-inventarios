package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    int countByDisponibilityAndCategoriaId(boolean disponibility, Long categoriaId);
} 
