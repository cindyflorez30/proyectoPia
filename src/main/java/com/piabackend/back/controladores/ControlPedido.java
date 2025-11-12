package com.piabackend.back.controladores;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piabackend.back.entidades.Pedido;
import com.piabackend.back.servicios.ServiPedido;

@RestController
@RequestMapping("/api/pedidos")
public class ControlPedido {

    private final ServiPedido serviPedido;

    public ControlPedido(ServiPedido serviPedido) {
        this.serviPedido = serviPedido;
    }

    /**
     * Obtiene todos los pedidos.
     * @return lista de pedidos con HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosPedidos() {
        List<Pedido> pedidos = serviPedido.listarPedido();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Obtiene un pedido por su ID.
     * @param id ID del pedido.
     * @return pedido encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Integer id) {
        Optional<Pedido> pedidoOpt = serviPedido.obtenerPedido(id);
        if (pedidoOpt.isPresent()) {
            return ResponseEntity.ok(pedidoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo pedido.
     * @param pedido datos del pedido a crear.
     * @return pedido creado con HTTP 201, o HTTP 400 si hay error.
     */
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        try {
            Pedido nuevoPedido = serviPedido.crearPedido(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un pedido existente.
     * @param id ID del pedido a actualizar.
     * @param pedido datos actualizados del pedido.
     * @return pedido actualizado con HTTP 200, o HTTP 404/400 según corresponda.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Optional<Pedido> existente = serviPedido.obtenerPedido(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            pedido.setIdPedido(id);
            Pedido pedidoActualizado = serviPedido.crearPedido(pedido);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un pedido por su ID.
     * @param id ID del pedido a eliminar.
     * @return HTTP 204 si se eliminó, o HTTP 404 si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        Optional<Pedido> existente = serviPedido.obtenerPedido(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviPedido.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
