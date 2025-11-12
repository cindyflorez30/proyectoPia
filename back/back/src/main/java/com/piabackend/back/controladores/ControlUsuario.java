package com.piabackend.back.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piabackend.back.entidades.Usuario;
import com.piabackend.back.repositorios.RepoUsuario;
import com.piabackend.back.servicios.ServiUsuario;


@RestController
@RequestMapping("/api/usuarios")
public class ControlUsuario {
    @Autowired
    private final ServiUsuario serviUsuario;
    @Autowired
    private RepoUsuario repoUsuario; // para login

    // Inyección de dependencias del servicio de usuario (capa de negocio)
    public ControlUsuario(ServiUsuario serviUsuario) {
        this.serviUsuario = serviUsuario;
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * @return lista de usuarios con HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> usuarios = serviUsuario.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario.
     * @return usuario encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuarioOpt = serviUsuario.obtenerUsuario(id);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            // Retorna 404 Not Found si no se encuentra el usuario
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo usuario.
     * @param usuario datos del usuario a crear.
     * @return usuario creado con HTTP 201 (Created), o HTTP 400 si hay error en los datos.
     */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = serviUsuario.crearUsuario(usuario);
            // Retorna 201 Created y el usuario creado en el cuerpo
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            // En caso de error (por ejemplo, dato inválido), retorna 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un usuario existente.
     * @param id ID del usuario a actualizar.
     * @param usuario datos actualizados del usuario.
     * @return usuario actualizado con HTTP 200, o HTTP 404/400 según corresponda.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> existente = serviUsuario.obtenerUsuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
        try {
            usuario.setIdUsuario(id);
            Usuario usuarioActualizado = serviUsuario.crearUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            // Error en la actualización: retorna 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     * @return HTTP 204 (No Content) si se eliminó, o HTTP 404 si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Optional<Usuario> existente = serviUsuario.obtenerUsuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404
            return ResponseEntity.notFound().build();
        }
        serviUsuario.eliminarUsuario(id);
        // Retorna 204 No Content
        return ResponseEntity.noContent().build();
    }

    // Endpoint de login: valida correo y contraseña
    @GetMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String correo, @RequestParam String password) {
        Usuario u = repoUsuario.findByCorreo(correo);
        if (u != null && u.getPassword().equals(password)) {
            return ResponseEntity.ok(u);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

