package com.inventario.sistema_inventario.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.inventario.sistema_inventario.models.Preguntas.EvaluacionRespuestas;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "id_usuario_solicitante", nullable = false)
    private long idUsuarioSolicitante;

    @Column(name = "id_usuario_aprobacion", nullable = true)
    private Long idUsuarioAprobacion;

    @Column(name = "fecha_entrega", nullable = true)
    private LocalDateTime dateEntrega;

    @Column(name = "fecha_termino", nullable = true)
    private LocalDateTime dateTermino;

    @OneToOne
    @JoinColumn(name = "id_eval_respuestas")
    private EvaluacionRespuestas idEvaluacionRespuestas;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudProducto> solicitudProductos = new ArrayList<>();

    @Column(name = "estado_solicitud", nullable = false)
    private int estadoSolicitud;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    public Solicitud(){
        this.idUsuarioSolicitante = 2;
        this.estadoSolicitud = 1;
        this.date = LocalDateTime.now();
    }

}
