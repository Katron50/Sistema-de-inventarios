package com.inventario.sistema_inventario.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    boolean existsByName(String name); // Para verificar si existe una categoria con ese nombre

    List<Categoria> findByDisponibility(boolean disponibility);
}
