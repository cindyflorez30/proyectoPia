package com.piabackend.back.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piabackend.back.entidades.DetallePedido;
import com.piabackend.back.repositorios.RepoDetallePedido;


@Service
public class ServiDetallePedido {
    @Autowired
    private RepoDetallePedido detallePedido;

    public List<DetallePedido> listarDetallePedido() {
        return detallePedido.findAll();
    }
    public DetallePedido crearDetallePedido(DetallePedido u) {
        return detallePedido.save(u);
    }
    public Optional<DetallePedido> obtenerDetallePedido(int id) {
        return detallePedido.findById(id);
    }
    public void eliminarDetallePedido(int id) {
        detallePedido.deleteById(id);
    }
}
