package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.HabitoDAO;
import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static org.HuellaCarbono.model.DAO.DAO.sF;

public class HabitoService {
    private HabitoDAO habitoDAO;

    public HabitoService() {
        this.habitoDAO = new HabitoDAO();
    }

    public boolean saveHabito(Habito habito) {
        Session session = sF.openSession();
        try {
            session.beginTransaction();

            Actividad managedActividad = (Actividad) session.merge(habito.getIdActividad());
            habito.setIdActividad(managedActividad);

            if (habito.getId() == null) {
                HabitoId habitoId = new HabitoId();
                habitoId.setIdUsuario(habito.getIdUsuario().getId());
                habitoId.setIdActividad(managedActividad.getId());
                habito.setId(habitoId);
                habitoDAO.insert(habito);
            } else {
                Habito existingHabito = habitoDAO.findById(habito.getId());
                if (existingHabito == null) {
                    habitoDAO.insert(habito);
                } else {
                    habitoDAO.update(habito);
                }
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public Habito getHabitoById(HabitoId id) {
        if (id == null || id.getIdUsuario() <= 0 || id.getIdActividad() <= 0) {
            return null;
        }
        return habitoDAO.findById(id);
    }

    public List<Habito> getAllHabitos() {
        return habitoDAO.findAllWithActividad();
    }
}