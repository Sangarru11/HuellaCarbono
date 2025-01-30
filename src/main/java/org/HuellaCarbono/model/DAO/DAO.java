package org.HuellaCarbono.model.DAO;

import org.HuellaCarbono.model.entity.Actividad;
import org.HuellaCarbono.model.entity.Habito;
import org.HuellaCarbono.model.entity.HabitoId;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> extends Closeable {
    SessionFactory sF = new Configuration().configure().buildSessionFactory();

    boolean save(T entity);
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(T entity) throws SQLException;

    T findById(Integer id);

    Habito findById(HabitoId id);

    List<T> findAll();
}