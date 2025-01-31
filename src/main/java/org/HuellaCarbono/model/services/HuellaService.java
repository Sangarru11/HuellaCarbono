package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.HuellaDAO;
import org.HuellaCarbono.model.entity.Huella;

import java.util.List;

public class HuellaService {
    private HuellaDAO huellaDAO;

    public HuellaService() {
        this.huellaDAO = new HuellaDAO();
    }

    public boolean saveHuella(Huella huella) {
        if (huellaDAO.findById(huella.getId()) == null) {
            List<Huella> existingHuellas = huellaDAO.findAll();
            for (Huella existingHuella : existingHuellas) {
                if (existingHuella.getFecha().equals(huella.getFecha()) &&
                    existingHuella.getIdUsuario().equals(huella.getIdUsuario()) &&
                    existingHuella.getIdActividad().equals(huella.getIdActividad())) {
                    return false;
                }
            }
            return huellaDAO.insert(huella);
        }
        return false;
    }

    public Huella getHuellaById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Huella huella = huellaDAO.findById(id);
        if (huella == null) {
            return null;
        }
        List<Huella> existingHuellas = huellaDAO.findAll();
        for (Huella existingHuella : existingHuellas) {
            if (existingHuella.getId().equals(id) && !existingHuella.equals(huella)) {
                return null;
            }
        }
        return huella;
    }

    public List<Huella> getAllHuellas() {
        List<Huella> huellas = huellaDAO.findAll();
        if (huellas == null || huellas.isEmpty()) {
            return null;
        }
        return huellas;
    }
}