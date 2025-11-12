package com.piabackend.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piabackend.back.entidades.DetallePedido;

@Repository
public interface RepoDetallePedido extends JpaRepository<DetallePedido, Integer> {
    
}