package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.HabitoDAO;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;

import java.util.List;

public class HabitoService {
    private HabitoDAO habitoDAO;

    public HabitoService() {
        this.habitoDAO = new HabitoDAO();
    }

    public boolean saveHabito(Habito habito) {
        if (habitoDAO.findById(habito.getId()) == null) {
            List<Habito> existingHabitos = habitoDAO.findAll();
            for (Habito existingHabito : existingHabitos) {
                if (existingHabito.getId().equals(habito.getId())) {
                    return false; // Duplicado encontrado
                }
            }
            return habitoDAO.insert(habito);
        }
        return false;
    }

    public Habito getHabitoById(HabitoId id) {
        if (id == null || id.getIdUsuario() <= 0 || id.getIdActividad() <= 0) {
            return null; // ID inválida
        }
        Habito habito = habitoDAO.findById(id);
        if (habito == null) {
            return null; // Hábito no encontrado
        }
        List<Habito> existingHabitos = habitoDAO.findAll();
        for (Habito existingHabito : existingHabitos) {
            if (existingHabito.getId().equals(id) && !existingHabito.equals(habito)) {
                return null; // ID duplicada encontrada
            }
        }
        return habito;
    }

    public List<Habito> getAllHabitos() {
        List<Habito> habitos = habitoDAO.findAll();
        if (habitos == null || habitos.isEmpty()) {
            return null; // No se encontraron hábitos
        }
        return habitos;
    }
}