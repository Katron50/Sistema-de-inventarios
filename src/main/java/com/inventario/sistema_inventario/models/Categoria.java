package com.inventario.sistema_inventario.models;


import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")  // Asegura que no sea vacío ni espacios en blanco
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Size(max = 250, message = "La descripción no puede tener más de 50 caracteres")
    @Column(name = "description", nullable = true, length = 250)
    private String description;

    @Column(name = "created_at", nullable = false, length = 50)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibility;


    public Categoria(){
        this.date = LocalDateTime.now();
        this.disponibility = true;
    }

}
