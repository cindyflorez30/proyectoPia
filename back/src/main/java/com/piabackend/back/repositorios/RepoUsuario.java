package com.piabackend.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piabackend.back.entidades.Usuario;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Integer> {
    // Método adicional para login
    Usuario findByCorreo(String correo);
}
