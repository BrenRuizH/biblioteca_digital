package com.biblioteca.revista.repository;

import com.biblioteca.revista.model.Revista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevistaRepository extends MongoRepository<Revista, String> {
    List<Revista> findByTituloContainingIgnoreCase(String titulo);
    List<Revista> findByNoEdicionContainingIgnoreCase(String noEdicion);
    List<Revista> findByCategoriaContainingIgnoreCase(String categoria);
}
