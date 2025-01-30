package org.HuellaCarbono.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categoria", schema = "huellacarbono")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer id;

    @ColumnDefault("'0'")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ColumnDefault("'0'")
    @Column(name = "factor_emision", nullable = false, length = 50)
    private String factorEmision;

    @Column(name = "unidad", length = 50)
    private String unidad;

    @OneToMany(mappedBy = "idCategoria")
    private Set<Actividad> actividads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCategoria")
    private Set<org.HuellaCarbono.model.entity.Recomendacion> recomendacions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(String factorEmision) {
        this.factorEmision = factorEmision;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Set<Actividad> getActividads() {
        return actividads;
    }

    public void setActividads(Set<Actividad> actividads) {
        this.actividads = actividads;
    }

    public Set<org.HuellaCarbono.model.entity.Recomendacion> getRecomendacions() {
        return recomendacions;
    }

    public void setRecomendacions(Set<org.HuellaCarbono.model.entity.Recomendacion> recomendacions) {
        this.recomendacions = recomendacions;
    }

}