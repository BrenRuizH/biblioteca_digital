package com.biblioteca.usuario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.usuario.model.Usuario;
import com.biblioteca.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<Usuario> getUsuarioById(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Usuario createUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardar el usuario
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<Usuario> updateUsuario(String id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        // Encriptar la contraseña si se actualiza
        if (usuario.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(encodedPassword);
        }
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }
    public ResponseEntity<Void> deleteUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Usuario> searchUsuarios(String nombre, String email) {
        if (nombre != null) {
            return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
        }
        if (email != null) {
            return usuarioRepository.findByEmailContainingIgnoreCase(email);
        }
        return getAllUsuarios();
    }

    public List<Usuario> getUsuariosSortedByRol() {
        return usuarioRepository.findAll().stream()
                .sorted((u1, u2) -> u1.getRol().compareTo(u2.getRol()))
                .collect(Collectors.toList());
    }
}
