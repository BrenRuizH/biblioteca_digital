package com.biblioteca.usuario.controller;

import com.biblioteca.usuario.model.Usuario;
import com.biblioteca.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable String id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        return usuarioService.deleteUsuario(id);
    }

    @GetMapping("/search")
    public List<Usuario> searchUsuarios(@RequestParam(required = false) String nombre,
                                        @RequestParam(required = false) String email) {
        return usuarioService.searchUsuarios(nombre, email);
    }

    @GetMapping("/sorted")
    public List<Usuario> getUsuariosSortedByRol() {
        return usuarioService.getUsuariosSortedByRol();
    }
}
