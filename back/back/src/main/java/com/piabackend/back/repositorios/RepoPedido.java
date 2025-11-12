package com.piabackend.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piabackend.back.entidades.Pedido;

@Repository
public interface RepoPedido extends JpaRepository<Pedido, Integer> {
    
}