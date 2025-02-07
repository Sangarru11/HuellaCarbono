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
        if (huella.getId() == null) {
            return huellaDAO.insert(huella);
        } else {
            Huella existingHuella = huellaDAO.findById(huella.getId());
            if (existingHuella == null) {
                return huellaDAO.insert(huella);
            } else {
                return huellaDAO.update(huella);
            }
        }
    }

    public Huella getHuellaById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return huellaDAO.findById(id);
    }

    public List<Huella> getAllHuellas() {
        return huellaDAO.findAll();
    }

    public List<Huella> getHuellasByUserId(Integer userId) {
        return huellaDAO.findByUserId(userId);
    }
}