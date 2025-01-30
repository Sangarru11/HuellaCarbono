package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.ActividadDAO;
import org.HuellaCarbono.model.entity.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDAO actividadDAO;

    public ActividadService() {
        this.actividadDAO = new ActividadDAO();
    }

    public boolean saveActividad(Actividad actividad) {
        if (actividadDAO.findById(actividad.getId()) == null) {
            List<Actividad> existingActividades = actividadDAO.findAll();
            for (Actividad existingActividad : existingActividades) {
                if (existingActividad.getNombre().equals(actividad.getNombre()) &&
                    existingActividad.getIdCategoria().equals(actividad.getIdCategoria())) {
                    return false;
                }
            }
            return actividadDAO.insert(actividad);
        }
        return false;
    }

    public Actividad getActividadById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Actividad actividad = actividadDAO.findById(id);
        if (actividad == null) {
            return null;
        }
        List<Actividad> existingActividades = actividadDAO.findAll();
        for (Actividad existingActividad : existingActividades) {
            if (existingActividad.getId().equals(id) && !existingActividad.equals(actividad)) {
                return null;
            }
        }
        return actividad;
    }

    public List<Actividad> getAllActividades() {
        List<Actividad> actividades = actividadDAO.findAll();
        if (actividades == null || actividades.isEmpty()) {
            return null;
        }
        return actividades;
    }
}