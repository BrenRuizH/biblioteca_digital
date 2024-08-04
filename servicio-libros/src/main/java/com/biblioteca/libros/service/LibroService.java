package com.biblioteca.libros.service;

import com.biblioteca.libros.model.Libro;
import com.biblioteca.libros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    public Libro getLibroById(String id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro updateLibro(String id, Libro libro) {
        if (!libroRepository.existsById(id)) {
            return null; // Indicar que el libro no existe
        }
        libro.setId(id);
        return libroRepository.save(libro);
    }

    public boolean deleteLibro(String id) {
        if (!libroRepository.existsById(id)) {
            return false; // Indicar que el libro no existe
        }
        libroRepository.deleteById(id);
        return true;
    }

    public List<Libro> searchLibros(String titulo, String autor, String categoria) {
        if (titulo != null) {
            return libroRepository.findByTituloContainingIgnoreCase(titulo);
        }
        if (autor != null) {
            return libroRepository.findByAutorContainingIgnoreCase(autor);
        }
        if (categoria != null) {
            return libroRepository.findByCategoriaContainingIgnoreCase(categoria);
        }
        return getAllLibros();
    }

    public List<Libro> getLibrosSortedByFechaPublicacion() {
        return libroRepository.findAll().stream()
                .sorted((l1, l2) -> l2.getFechaPublicacion().compareTo(l1.getFechaPublicacion()))
                .collect(Collectors.toList());
    }
}
