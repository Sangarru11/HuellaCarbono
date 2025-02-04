package org.HuellaCarbono.model.services;

import org.HuellaCarbono.model.DAO.UsuarioDAO;
import org.HuellaCarbono.model.entity.Usuario;

import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean saveUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            return usuarioDAO.insert(usuario);
        } else {
            Usuario existingUsuario = usuarioDAO.findById(usuario.getId());
            if (existingUsuario == null) {
                return usuarioDAO.insert(usuario);
            } else {
                return usuarioDAO.update(usuario);
            }
        }
    }

    public Usuario getUsuarioById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return usuarioDAO.findById(id);
    }

    public Usuario getUsuarioByUsername(String username) {
        return usuarioDAO.findByName(username);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.findAll();
    }
}