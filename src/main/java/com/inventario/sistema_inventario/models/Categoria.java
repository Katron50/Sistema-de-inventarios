package com.inventario.sistema_inventario.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Categoria {

    private long id;
    private String name;
    private String description;
    private String date;
    private boolean disponibility;
}
