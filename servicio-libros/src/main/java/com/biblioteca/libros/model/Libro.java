package com.biblioteca.libros.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "libros")
public class Libro {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String categoria;
    private LocalDate fechaPublicacion;

    public Libro(String id, String titulo, String autor, String categoria, String fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.fechaPublicacion = LocalDate.parse(fechaPublicacion);
    }
}
