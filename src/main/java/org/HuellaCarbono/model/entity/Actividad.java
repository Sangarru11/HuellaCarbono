package org.HuellaCarbono.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actividad", schema = "huellacarbono")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @ColumnDefault("'0'")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("0")
    @JoinColumn(name = "id_categoria", nullable = false)
    private org.HuellaCarbono.model.entity.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<org.HuellaCarbono.model.entity.Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<org.HuellaCarbono.model.entity.Huella> huellas = new LinkedHashSet<>();

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

    public org.HuellaCarbono.model.entity.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(org.HuellaCarbono.model.entity.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<org.HuellaCarbono.model.entity.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<org.HuellaCarbono.model.entity.Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<org.HuellaCarbono.model.entity.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<org.HuellaCarbono.model.entity.Huella> huellas) {
        this.huellas = huellas;
    }

}