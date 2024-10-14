package com.inventario.sistema_inventario.models;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "clave", nullable = false, length = 15, unique = true)
    @Pattern(regexp = "^[^\\s]{15}$", message = "La clave debe tener exactamente 15 caracteres y no contener espacios.")
    private String productKey;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Size(max = 50, message = "El nombre del producto no puede tener más de 50 caracteres.")
    private String name;

    @Column(name = "descripcion", nullable = true, length = 250)
    @Size(max = 250, message = "La descripción no puede tener más de 250 caracteres.")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Categoria categoria;

    @Column(name = "tolerancia", nullable = false)
    @PositiveOrZero(message = "La tolerancia debe ser un número positivo.")
    private Integer tolerance;

    @Column(name = "imagen", nullable = true)
    @Pattern(regexp = ".*\\.(jpeg|jpg|png)$", message = "La imagen debe tener formato .jpeg, .jpg o .png.")
    private String image;

    @Transient
    private MultipartFile imageFile; // Para manejar el archivo de imagen

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibility;


    public Producto(){
        this.disponibility = true;
        this.date = LocalDateTime.now();
    }
    
}
