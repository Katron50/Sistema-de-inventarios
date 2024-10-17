package com.inventario.sistema_inventario.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "comentarios")
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false) 
    private Compra compra;

    @Size(max = 250, message = "La descripción no puede tener más de 250 caracteres.")
    @Column(name = "comentario", nullable = false)
    private String comentario;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    public Comentario(){
        this.date = LocalDateTime.now();
    }

}
