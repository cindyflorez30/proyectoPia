package com.piabackend.back.controladores;

// Ejemplo completo de ProductoController

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

import com.piabackend.back.entidades.Producto;
import com.piabackend.back.servicios.ServiProducto;

@RestController
@RequestMapping("/api/productos")
public class ControlProducto {

    private final ServiProducto serviProducto;

    public ControlProducto(ServiProducto serviProducto) {
        this.serviProducto = serviProducto;
    }

    /**
     * Obtiene todos los productos.
     * @return lista de productos con HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        List<Producto> productos = serviProducto.listarProductos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto.
     * @return producto encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        Optional<Producto> productoOpt = serviProducto.obtenerProducto(id);
        if (productoOpt.isPresent()) {
            return ResponseEntity.ok(productoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo producto.
     * @param producto datos del producto a crear.
     * @return producto creado con HTTP 201, o HTTP 400 si hay error.
     */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = serviProducto.crearProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar.
     * @param producto datos actualizados del producto.
     * @return producto actualizado con HTTP 200, o HTTP 404/400 según corresponda.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Optional<Producto> existente = serviProducto.obtenerProducto(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            producto.setIdProducto(id);
            Producto productoActualizado = serviProducto.crearProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     * @return HTTP 204 si se eliminó, o HTTP 404 si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        Optional<Producto> existente = serviProducto.obtenerProducto(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviProducto.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}