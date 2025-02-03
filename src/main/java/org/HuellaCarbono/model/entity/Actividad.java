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
    private Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<Huella> huellas = new LinkedHashSet<>();

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

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<Huella> huellas) {
        this.huellas = huellas;
    }

    @Override
    public String toString() {
        return nombre;
    }
}