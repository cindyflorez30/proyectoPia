package com.piabackend.back.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piabackend.back.entidades.Usuario;
import com.piabackend.back.repositorios.RepoUsuario;

@Service
public class ServiUsuario {
    @Autowired
    private RepoUsuario repoUsuario;

    public List<Usuario> listarUsuarios() {
        return repoUsuario.findAll();
    }
    public Usuario crearUsuario(Usuario u) {
        return repoUsuario.save(u);
    }
    public Optional<Usuario> obtenerUsuario(int id) {
        return repoUsuario.findById(id);
    }
    public void eliminarUsuario(int id) {
        repoUsuario.deleteById(id);
    }
    // Otros métodos según se requiera...

}