package com.biblioteca.prestamo.repository;

import com.biblioteca.prestamo.model.Prestamo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends MongoRepository<Prestamo, String> {
    List<Prestamo> findByIdUsuario(String idUsuario);
    List<Prestamo> findByIdLibro(String idLibro);
    List<Prestamo> findByIdRevista(String idRevista);
}
