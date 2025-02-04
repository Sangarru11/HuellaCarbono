package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.ActividadDAO;
import org.HuellaCarbono.model.entity.Actividad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ActividadService {
    private ActividadDAO actividadDAO;
    private SessionFactory sessionFactory;

    public ActividadService() {
        this.actividadDAO = new ActividadDAO();
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public boolean saveActividad(Actividad actividad) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (actividadDAO.findById(actividad.getId()) == null) {
                List<Actividad> existingActividades = actividadDAO.findAll();
                for (Actividad existingActividad : existingActividades) {
                    if (existingActividad.getNombre().equals(actividad.getNombre()) &&
                        existingActividad.getIdCategoria().equals(actividad.getIdCategoria())) {
                        return false;
                    }
                }
                session.persist(actividad);
            } else {
                session.merge(actividad);
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

    public Actividad getActividadById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Actividad actividad = session.get(Actividad.class, id);
            session.getTransaction().commit();
            return actividad;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Actividad> getAllActividades() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
            session.getTransaction().commit();
            return actividades;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}