package com.cesde.proyecta.model.entity;

import com.cesde.proyecta.model.enums.EstadoReto;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "retos")
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_limite_rpta", nullable = false)
    private LocalDateTime fechaLimiteRpta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_actual", length = 50, nullable = false)
    private EstadoReto estadoActual;

    // --- RELACIONES (Llaves Foráneas) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaAcademica categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;

    // Constructor vacío exigido por JPA
    public Reto() {
    }

    // --- GETTERS Y SETTERS MANUALES (Estilo del equipo) ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public LocalDateTime getFechaLimiteRpta() { return fechaLimiteRpta; }
    public void setFechaLimiteRpta(LocalDateTime fechaLimiteRpta) { this.fechaLimiteRpta = fechaLimiteRpta; }

    public EstadoReto getEstadoActual() { return estadoActual; }
    public void setEstadoActual(EstadoReto estadoActual) { this.estadoActual = estadoActual; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public CategoriaAcademica getCategoria() { return categoria; }
    public void setCategoria(CategoriaAcademica categoria) { this.categoria = categoria; }

    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
}
