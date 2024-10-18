package com.inventario.sistema_inventario.models.Preguntas;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuestionarios")
public class Cuestionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", nullable = false, length = 100)
    @Size(max = 100)
    @NotNull(message = "No puede ser estar vacio el nombre")
    private String name;

    @OneToMany(mappedBy = "cuestionario", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    public Cuestionario(){
        this.date = LocalDateTime.now();
    }
}
