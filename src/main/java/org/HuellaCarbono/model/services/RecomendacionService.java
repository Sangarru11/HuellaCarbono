package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.RecomendacionDAO;
import org.HuellaCarbono.model.entity.Recomendacion;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionService {
    private RecomendacionDAO recomendacionDAO;

    public RecomendacionService() {
        this.recomendacionDAO = new RecomendacionDAO();
    }

    public boolean saveRecomendacion(Recomendacion recomendacion) {
        if (recomendacion.getId() == null) {
            return recomendacionDAO.insert(recomendacion);
        } else {
            Recomendacion existingRecomendacion = recomendacionDAO.findById(recomendacion.getId());
            if (existingRecomendacion == null) {
                return recomendacionDAO.insert(recomendacion);
            } else {
                return recomendacionDAO.update(recomendacion);
            }
        }
    }

    public Recomendacion getRecomendacionById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return recomendacionDAO.findById(id);
    }

    public List<Recomendacion> getAllRecomendaciones() {
        return recomendacionDAO.findAll();
    }

    public List<Recomendacion> getRecomendacionesByCategoriaIds(List<Integer> categoriaIds) {
        return getAllRecomendaciones().stream()
                .filter(recomendacion -> categoriaIds.contains(recomendacion.getIdCategoria().getId()))
                .collect(Collectors.toList());
    }
}