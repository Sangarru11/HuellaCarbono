package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HabitoDAO implements DAO<Habito, HabitoId> {
    @Override
    public boolean save(Habito entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.saveOrUpdate(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean insert(Habito entity) {
        Session sn = sF.openSession();
        Transaction tx = sn.beginTransaction();
        try {
            if (entity.getIdActividad() != null) {
                entity.setIdActividad(sn.merge(entity.getIdActividad()));
            }
            if (entity.getIdUsuario() != null) {
                entity.setIdUsuario(sn.merge(entity.getIdUsuario()));
            }
            sn.persist(entity);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            sn.close();
        }
    }

    @Override
    public boolean update(Habito entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.update(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean delete(Habito entity) throws SQLException {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.delete(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public Habito findById(Integer id) {
        return null;
    }

    @Override
    public Habito findById(HabitoId id) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        Habito habito = ss.get(Habito.class, id);
        ss.getTransaction().commit();
        ss.close();
        return habito;
    }

    @Override
    public List<Habito> findAll() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Habito> habitos = ss.createQuery("from Habito", Habito.class).list();
        ss.getTransaction().commit();
        ss.close();
        return habitos;
    }

    public List<Habito> findAllWithActividad() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Habito> habitos = ss.createQuery("SELECT h FROM Habito h JOIN FETCH h.idActividad", Habito.class).list();
        ss.getTransaction().commit();
        ss.close();
        return habitos;
    }

    @Override
    public void close() throws IOException {
        sF.close();
    }

    public static HabitoDAO build() {
        return new HabitoDAO();
    }
}