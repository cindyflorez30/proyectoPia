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

import com.piabackend.back.entidades.DetallePedido;
import com.piabackend.back.servicios.ServiDetallePedido;



@RestController
@RequestMapping("/api/detalles-pedido")
public class ControlDetallePedido {

    private final ServiDetallePedido serviDetallePedido;

    public ControlDetallePedido(ServiDetallePedido serviDetallePedido) {
        this.serviDetallePedido = serviDetallePedido;
    }

    /**
     * Obtiene todos los detalles de pedido.
     * @return lista de detalles con HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<DetallePedido>> obtenerTodosDetalles() {
        List<DetallePedido> detalles = serviDetallePedido.listarDetallePedido();
        return ResponseEntity.ok(detalles);
    }

    /**
     * Obtiene un detalle de pedido por su ID.
     * @param id ID del detalle de pedido.
     * @return detalle encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerDetallePorId(@PathVariable Integer id) {
        Optional<DetallePedido> detalleOpt = serviDetallePedido.obtenerDetallePedido(id);
        if (detalleOpt.isPresent()) {
            return ResponseEntity.ok(detalleOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo detalle de pedido.
     * @param detalle datos del detalle a crear.
     * @return detalle creado con HTTP 201, o HTTP 400 si hay error.
     */
    @PostMapping
    public ResponseEntity<DetallePedido> crearDetalle(@RequestBody DetallePedido detalle) {
        try {
            DetallePedido nuevoDetalle = serviDetallePedido.crearDetallePedido(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un detalle de pedido existente.
     * @param id ID del detalle a actualizar.
     * @param detalle datos actualizados del detalle.
     * @return detalle actualizado con HTTP 200, o HTTP 404/400 según corresponda.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(@PathVariable Integer id, @RequestBody DetallePedido detalle) {
        Optional<DetallePedido> existente = serviDetallePedido.obtenerDetallePedido(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            detalle.setIdDetalle(id);
            DetallePedido detalleActualizado = serviDetallePedido.crearDetallePedido(detalle);
            return ResponseEntity.ok(detalleActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un detalle de pedido por su ID.
     * @param id ID del detalle a eliminar.
     * @return HTTP 204 si se eliminó, o HTTP 404 si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        Optional<DetallePedido> existente = serviDetallePedido.obtenerDetallePedido(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviDetallePedido.eliminarDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
}
