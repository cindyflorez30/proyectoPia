package com.piabackend.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piabackend.back.entidades.Producto;

@Repository
public interface RepoProducto extends JpaRepository<Producto, Integer> {
    
}