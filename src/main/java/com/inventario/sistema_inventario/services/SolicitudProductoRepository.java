package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.SolicitudProducto;

public interface SolicitudProductoRepository  extends JpaRepository<SolicitudProducto, Long>{
    
}
