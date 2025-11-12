package com.piabackend.back.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piabackend.back.entidades.Producto;
import com.piabackend.back.repositorios.RepoProducto;


@Service
public class ServiProducto {
    @Autowired
    private RepoProducto repoProducto;

    public List<Producto> listarProductos() {
        return repoProducto.findAll();
    }
    public Producto crearProducto(Producto u) {
        return repoProducto.save(u);
    }
    public Optional<Producto> obtenerProducto(int id) {
        return repoProducto.findById(id);
    }
    public void eliminarProducto(int id) {
        repoProducto.deleteById(id);
    }
    // Otros métodos según se requiera...
}
