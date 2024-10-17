package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Comentario;

import java.util.List;
import java.util.Optional;


public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

    Optional<List<Comentario>> findByCompra_Id(Long compraId);

} 
