package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Categoria;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.hibernate.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDAO implements DAO<Categoria, String> {
    @Override
    public boolean save(Categoria entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.saveOrUpdate(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean insert(Categoria entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.persist(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean update(Categoria entity) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.update(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public boolean delete(Categoria entity) throws SQLException {
        Session ss = sF.openSession();
        ss.beginTransaction();
        ss.delete(entity);
        ss.getTransaction().commit();
        ss.close();
        return true;
    }

    @Override
    public Categoria findById(Integer id) {
        Session ss = sF.openSession();
        ss.beginTransaction();
        Categoria categoria = ss.get(Categoria.class, id);
        ss.getTransaction().commit();
        ss.close();
        return categoria;
    }

    @Override
    public Habito findById(HabitoId id) {
        return null;
    }

    @Override
    public List<Categoria> findAll() {
        Session ss = sF.openSession();
        ss.beginTransaction();
        List<Categoria> categorias = ss.createQuery("from Categoria", Categoria.class).list();
        ss.getTransaction().commit();
        ss.close();
        return categorias;
    }

    @Override
    public void close() throws IOException {
        sF.close();
    }
}