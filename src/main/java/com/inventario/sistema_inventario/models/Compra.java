package com.inventario.sistema_inventario.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "compras")
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)

    @NotNull(message = "No puede haber una compra sin producto")
    private Producto producto;

    @Column(name = "cantidadComprada", nullable = false)
    @PositiveOrZero(message = "La cantidad comprada debe ser un número positivo.")
    private double cantidadComprada;

    @Column(name = "cantidadActual", nullable = false)
    @PositiveOrZero(message = "La cantidad actual no puede ser negativo.")
    private double cantidadActual;

    @Column(name = "costoCompra", nullable = false)
    @PositiveOrZero(message = "El costo de compra debe ser un número positivo.")
    private double costoCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)  
    @JsonIgnore
    private List<Comentario> comentarios;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name ="fechaCompra", nullable = false)
    private LocalDate fechaCompra;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibility;
    
    
    public Compra(){
        this.date = LocalDateTime.now();
        this.disponibility = true;
    }

}


// @Entity
// @Table(name = "comentarios")
// public class Comentario {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "compra_id", nullable = false)
//     private Compra compra;

//     @Size(max = 250, message = "La descripción no puede tener más de 250 caracteres.")
//     @Column(name = "comentario", nullable = false, length = 250)
//     private String comentario;

//     private LocalDateTime fecha; // Para registrar cuándo se hizo el comentario

//     // Constructor, getters y setters
// }


// @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
// private List<Comentario> comentarios = new ArrayList<>();

// // Métodos para agregar un comentario
// public void agregarComentario(String texto) {
//     Comentario comentario = new Comentario();
//     comentario.setComentario(texto);
//     comentario.setCompra(this);
//     comentarios.add(comentario);
// }


// @Column(name = "comentarios", nullable = true)
// private String comentariosJson; // Almacena los comentarios en formato JSON

// public List<String> getComentarios() {
//     // Lógica para deserializar JSON a List<String>
// }

// public void agregarComentario(String comentario) {
//     List<String> comentarios = getComentarios();
//     comentarios.add(comentario);
//     this.comentariosJson = serializeToJson(comentarios); // Serializa de vuelta a JSON
// }