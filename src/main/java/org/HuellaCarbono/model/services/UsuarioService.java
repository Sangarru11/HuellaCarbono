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
        if (usuarioDAO.findById(usuario.getId()) == null) {
            List<Usuario> existingUsuarios = usuarioDAO.findAll();
            for (Usuario existingUsuario : existingUsuarios) {
                if (existingUsuario.getEmail().equals(usuario.getEmail())) {
                    return false;
                }
            }
            return usuarioDAO.insert(usuario);
        }
        return false;
    }

    public Usuario getUsuarioById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            return null;
        }
        List<Usuario> existingUsuarios = usuarioDAO.findAll();
        for (Usuario existingUsuario : existingUsuarios) {
            if (existingUsuario.getId().equals(id) && !existingUsuario.equals(usuario)) {
                return null;
            }
        }
        return usuario;
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = usuarioDAO.findAll();
        if (usuarios == null || usuarios.isEmpty()) {
            return null;
        }
        return usuarios;
    }
}