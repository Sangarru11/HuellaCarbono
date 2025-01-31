package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.RecomendacionDAO;
import org.HuellaCarbono.model.entity.Recomendacion;

import java.util.List;

public class RecomendacionService {
    private RecomendacionDAO recomendacionDAO;

    public RecomendacionService() {
        this.recomendacionDAO = new RecomendacionDAO();
    }

    public boolean saveRecomendacion(Recomendacion recomendacion) {
        if (recomendacionDAO.findById(recomendacion.getId()) == null) {
            List<Recomendacion> existingRecomendaciones = recomendacionDAO.findAll();
            for (Recomendacion existingRecomendacion : existingRecomendaciones) {
                if (existingRecomendacion.getDescripcion().equals(recomendacion.getDescripcion()) &&
                    existingRecomendacion.getIdCategoria().equals(recomendacion.getIdCategoria())) {
                    return false;
                }
            }
            return recomendacionDAO.insert(recomendacion);
        }
        return false;
    }

    public Recomendacion getRecomendacionById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Recomendacion recomendacion = recomendacionDAO.findById(id);
        if (recomendacion == null) {
            return null;
        }
        List<Recomendacion> existingRecomendaciones = recomendacionDAO.findAll();
        for (Recomendacion existingRecomendacion : existingRecomendaciones) {
            if (existingRecomendacion.getId().equals(id) && !existingRecomendacion.equals(recomendacion)) {
                return null;
            }
        }
        return recomendacion;
    }

    public List<Recomendacion> getAllRecomendaciones() {
        List<Recomendacion> recomendaciones = recomendacionDAO.findAll();
        if (recomendaciones == null || recomendaciones.isEmpty()) {
            return null;
        }
        return recomendaciones;
    }
}