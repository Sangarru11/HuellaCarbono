// HuellaDAO.java
package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.HuellaCarbono.model.entity.Huella;
import org.HuellaCarbono.model.entity.Usuario;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HuellaDAO implements DAO<Huella, String> {
    @Override
    public boolean save(Huella entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.saveOrUpdate(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean insert(Huella entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.persist(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean update(Huella entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.update(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean delete(Huella entity) throws SQLException {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.delete(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public Huella findById(Integer id) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        Huella huella = ss.get(Huella.class, id);
        ss.getTransaction().commit();
        ss.close();
        return huella;
    }

    @Override
    public Habito findById(HabitoId id) {
        return null;
    }

    @Override
    public List<Huella> findAll() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Huella> huellas = ss.createQuery("from Huella h join fetch h.idActividad a join fetch a.idCategoria", Huella.class).list();
        ss.getTransaction().commit();
        ss.close();
        return huellas;
    }

    public List<Huella> findByUserId(Integer userId) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Huella> huellas = ss.createQuery("from Huella h join fetch h.idActividad a join fetch a.idCategoria where h.idUsuario.id = :userId", Huella.class)
                .setParameter("userId", userId)
                .list();
        ss.getTransaction().commit();
        ss.close();
        return huellas;
    }

    public List<Huella> findByUser(Usuario usuario) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Huella> huellas = ss.createQuery("from Huella h join fetch h.idActividad a join fetch a.idCategoria where h.idUsuario = :usuario", Huella.class)
                .setParameter("usuario", usuario)
                .list();
        ss.getTransaction().commit();
        ss.close();
        return huellas;
    }

    @Override
    public void close() throws IOException {
        sF.close();
    }

    public static HuellaDAO build() {
        return new HuellaDAO();
    }
}