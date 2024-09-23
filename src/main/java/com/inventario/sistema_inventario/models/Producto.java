package com.inventario.sistema_inventario.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Producto {
    
    private long id;
    private String key;
    private String name;
    
}
