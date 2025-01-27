package org.HuellaCarbono.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "habito", schema = "huellacarbono")
public class Habito {
    @Column(name = "fecuencia", length = 50)
    private String fecuencia;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "ultima_fecha")
    private LocalDate ultimaFecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario")
    private org.HuellaCarbono.model.entity.Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_actividad")
    private Actividad idActividad;

    public String getFecuencia() {
        return fecuencia;
    }

    public void setFecuencia(String fecuencia) {
        this.fecuencia = fecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(LocalDate ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

    public org.HuellaCarbono.model.entity.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(org.HuellaCarbono.model.entity.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

}