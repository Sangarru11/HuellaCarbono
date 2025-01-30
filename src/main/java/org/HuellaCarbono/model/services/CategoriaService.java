package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.CategoriaDAO;
import org.HuellaCarbono.model.entity.Categoria;

import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean saveCategoria(Categoria categoria) {
        if (categoriaDAO.findById(categoria.getId()) == null) {
            List<Categoria> existingCategorias = categoriaDAO.findAll();
            for (Categoria existingCategoria : existingCategorias) {
                if (existingCategoria.getNombre().equals(categoria.getNombre())) {
                    return false; // Duplicado encontrado
                }
            }
            return categoriaDAO.insert(categoria);
        }
        return false;
    }

    public Categoria getCategoriaById(Integer id) {
        if (id == null || id <= 0) {
            return null; // ID inválida
        }
        Categoria categoria = categoriaDAO.findById(id);
        if (categoria == null) {
            return null; // Categoría no encontrada
        }
        List<Categoria> existingCategorias = categoriaDAO.findAll();
        for (Categoria existingCategoria : existingCategorias) {
            if (existingCategoria.getId().equals(id) && !existingCategoria.equals(categoria)) {
                return null; // ID duplicada encontrada
            }
        }
        return categoria;
    }

    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = categoriaDAO.findAll();
        if (categorias == null || categorias.isEmpty()) {
            return null; // No se encontraron categorías
        }
        return categorias;
    }
}