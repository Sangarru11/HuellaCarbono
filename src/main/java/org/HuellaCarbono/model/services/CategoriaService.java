package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.CategoriaDAO;
import org.HuellaCarbono.model.entity.Categoria;
import org.HuellaCarbono.model.entity.Usuario;

import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean saveCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            // Insertar nueva categoría
            List<Categoria> existingCategorias = categoriaDAO.findAll();
            for (Categoria existingCategoria : existingCategorias) {
                if (existingCategoria.getNombre().equals(categoria.getNombre())) {
                    return false; // Duplicado encontrado
                }
            }
            return categoriaDAO.insert(categoria);
        } else {
            // Actualizar categoría existente
            Categoria existingCategoria = categoriaDAO.findById(categoria.getId());
            if (existingCategoria == null) {
                return false; // Categoría no encontrada
            }
            return categoriaDAO.update(categoria);
        }
    }

    public Categoria getCategoriaById(Integer id) {
        if (id == null || id <= 0) {
            return null; // ID inválida
        }
        return categoriaDAO.findById(id);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaDAO.findAll();
    }

    public List<Categoria> getCategoriasByNames(String... names) {
        return categoriaDAO.findByNames(names);
    }
}