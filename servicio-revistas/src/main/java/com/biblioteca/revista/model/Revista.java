package com.biblioteca.revista.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "revistas")
public class Revista {

    @Id
    private String id;
    private String titulo;
    private String noEdicion;
    private String categoria;
    private LocalDate fechaPublicacion;
}
