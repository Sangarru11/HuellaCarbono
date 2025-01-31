package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.HuellaCarbono.model.entity.Recomendacion;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecomendacionDAO implements DAO<Recomendacion, String> {
    @Override
    public boolean save(Recomendacion entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.saveOrUpdate(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean insert(Recomendacion entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.persist(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean update(Recomendacion entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.update(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean delete(Recomendacion entity) throws SQLException {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.delete(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public Recomendacion findById(Integer id) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        Recomendacion recomendacion = ss.get(Recomendacion.class, id);
        ss.getTransaction().commit();
        ss.close();
        return recomendacion;
    }

    @Override
    public Habito findById(HabitoId id) {
        return null;
    }

    @Override
    public List<Recomendacion> findAll() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Recomendacion> recomendaciones = ss.createQuery("from Recomendacion", Recomendacion.class).list();
        ss.getTransaction().commit();
        ss.close();
        return recomendaciones;
    }

    @Override
    public void close() throws IOException {
        sF.close();
    }
}