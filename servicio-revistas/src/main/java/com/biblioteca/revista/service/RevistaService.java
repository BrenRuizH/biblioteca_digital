package com.biblioteca.revista.service;

import com.biblioteca.revista.model.Revista;
import com.biblioteca.revista.repository.RevistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevistaService {

    @Autowired
    private RevistaRepository revistaRepository;

    public List<Revista> getAllRevistas() {
        return revistaRepository.findAll();
    }

    public ResponseEntity<Revista> getRevistaById(String id) {
        Optional<Revista> revista = revistaRepository.findById(id);
        return revista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Revista createRevista(Revista revista) {
        return revistaRepository.save(revista);
    }

    public ResponseEntity<Revista> updateRevista(String id, Revista revista) {
        if (!revistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        revista.setId(id);
        return ResponseEntity.ok(revistaRepository.save(revista));
    }

    public ResponseEntity<Void> deleteRevista(String id) {
        if (!revistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        revistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Revista> searchRevistas(String titulo, String noEdicion, String categoria) {
        if (titulo != null) {
            return revistaRepository.findByTituloContainingIgnoreCase(titulo);
        }
        if (noEdicion != null) {
            return revistaRepository.findByNoEdicionContainingIgnoreCase(noEdicion);
        }
        if (categoria != null) {
            return revistaRepository.findByCategoriaContainingIgnoreCase(categoria);
        }
        return getAllRevistas();
    }

    public List<Revista> getRevistasSortedByFechaPublicacion() {
        return revistaRepository.findAll().stream()
                .sorted((r1, r2) -> r2.getFechaPublicacion().compareTo(r1.getFechaPublicacion()))
                .collect(Collectors.toList());
    }
}
