package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
