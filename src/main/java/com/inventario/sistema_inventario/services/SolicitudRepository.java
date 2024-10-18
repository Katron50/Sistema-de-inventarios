package com.inventario.sistema_inventario.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.sistema_inventario.models.Solicitud;

public interface SolicitudRepository  extends JpaRepository<Solicitud, Long>{
    
}
