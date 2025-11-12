package com.piabackend.back.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piabackend.back.entidades.Pedido;
import com.piabackend.back.repositorios.RepoPedido;

@Service
public class ServiPedido {
    @Autowired
    private RepoPedido repoPedido;

    public List<Pedido> listarPedido() {
        return repoPedido.findAll();
    }
    public Pedido crearPedido(Pedido u) {
        return repoPedido.save(u);
    }
    public Optional<Pedido> obtenerPedido(int id) {
        return repoPedido.findById(id);
    }
    public void eliminarPedido(int id) {
        repoPedido.deleteById(id);
    }
}
