package com.biblioteca.usuario.repository;

import com.biblioteca.usuario.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByEmailContainingIgnoreCase(String email);
}
