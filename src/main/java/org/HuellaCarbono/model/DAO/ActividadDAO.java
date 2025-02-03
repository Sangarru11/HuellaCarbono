package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActividadDAO implements DAO<Actividad, Integer> {
    @Override
    public boolean save(Actividad entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.save(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean insert(Actividad entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.persist(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean update(Actividad entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.update(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean delete(Actividad entity) throws SQLException {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.delete(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public Actividad findById(Integer id) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        Actividad actividad = ss.get(Actividad.class, id);
        ss.getTransaction().commit();
        ss.close();
        return actividad;
    }

    @Override
    public Habito findById(HabitoId id) {
        return null;
    }

    @Override
    public List<Actividad> findAll() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Actividad> actividades = ss.createQuery("from Actividad", Actividad.class).list();
        ss.getTransaction().commit();
        ss.close();
        return actividades;
    }

    @Override
    public void close() throws IOException {
        sF.close();
    }
    public static ActividadDAO build() {
        return new ActividadDAO();
    }
}
