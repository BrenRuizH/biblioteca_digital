package com.biblioteca.prestamo.service;

import com.biblioteca.prestamo.model.Prestamo;
import com.biblioteca.prestamo.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    public ResponseEntity<Prestamo> getPrestamoById(String id) {
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);
        return prestamo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Prestamo createPrestamo(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    public ResponseEntity<Prestamo> updatePrestamo(String id, Prestamo prestamo) {
        if (!prestamoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        prestamo.setId(id);
        return ResponseEntity.ok(prestamoRepository.save(prestamo));
    }

    public ResponseEntity<Void> deletePrestamo(String id) {
        if (!prestamoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        prestamoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Prestamo> searchPrestamos(String idUsuario, String idLibro, String idRevista) {
        if (idUsuario != null) {
            return prestamoRepository.findByIdUsuario(idUsuario);
        }
        if (idLibro != null) {
            return prestamoRepository.findByIdLibro(idLibro);
        }
        if (idRevista != null) {
            return prestamoRepository.findByIdRevista(idRevista);
        }
        return getAllPrestamos();
    }

    public List<Prestamo> getPrestamosSortedByFechaPrestamo() {
        return prestamoRepository.findAll().stream()
                .sorted((p1, p2) -> p1.getFechaPrestamo().compareTo(p2.getFechaPrestamo()))
                .collect(Collectors.toList());
    }
}
