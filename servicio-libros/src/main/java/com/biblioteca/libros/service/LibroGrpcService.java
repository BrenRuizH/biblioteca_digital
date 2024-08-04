package com.biblioteca.libros.service;

import com.biblioteca.libros.grpc.LibroOuterClass;
import com.biblioteca.libros.grpc.LibroServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroGrpcService extends LibroServiceGrpc.LibroServiceImplBase {

    @Autowired
    private LibroService libroService;

    // Método para convertir entidad de dominio a clase protobuf
    private LibroOuterClass.Libro toGrpcLibro(com.biblioteca.libros.model.Libro libro) {
        LibroOuterClass.Libro.Builder builder = LibroOuterClass.Libro.newBuilder();

        // Asegurar que cada campo no sea nulo antes de construir el objeto
        if (libro.getId() != null) {
            builder.setId(libro.getId());
        }
        if (libro.getTitulo() != null) {
            builder.setTitulo(libro.getTitulo());
        }
        if (libro.getAutor() != null) {
            builder.setAutor(libro.getAutor());
        }
        if (libro.getCategoria() != null) {
            builder.setCategoria(libro.getCategoria());
        }
        if (libro.getFechaPublicacion() != null) {
            builder.setFechaPublicacion(String.valueOf(libro.getFechaPublicacion()));
        }

        return builder.build();
    }

    // Método para convertir clase protobuf a entidad de dominio
    private com.biblioteca.libros.model.Libro toDomainLibro(LibroOuterClass.Libro libro) {
        return new com.biblioteca.libros.model.Libro(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getCategoria(),
                libro.getFechaPublicacion()
        );
    }

    @Override
    public void getLibro(LibroOuterClass.LibroRequest request, StreamObserver<LibroOuterClass.LibroResponse> responseObserver) {
        try {
            System.out.println("Received request for Libro ID: " + request.getId());
            com.biblioteca.libros.model.Libro libro = libroService.getLibroById(request.getId());
            if (libro == null) {
                System.err.println("Libro no encontrado para ID: " + request.getId());
                responseObserver.onError(new RuntimeException("Libro no encontrado"));
                return;
            }
            System.out.println("Libro encontrado: " + libro);
            LibroOuterClass.Libro grpcLibro = toGrpcLibro(libro);
            LibroOuterClass.LibroResponse response = LibroOuterClass.LibroResponse.newBuilder().setLibro(grpcLibro).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }


    @Override
    public void createLibro(LibroOuterClass.Libro request, StreamObserver<LibroOuterClass.LibroResponse> responseObserver) {
        com.biblioteca.libros.model.Libro libro = toDomainLibro(request);
        com.biblioteca.libros.model.Libro createdLibro = libroService.createLibro(libro);
        LibroOuterClass.Libro grpcLibro = toGrpcLibro(createdLibro);
        LibroOuterClass.LibroResponse response = LibroOuterClass.LibroResponse.newBuilder().setLibro(grpcLibro).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateLibro(LibroOuterClass.Libro request, StreamObserver<LibroOuterClass.LibroResponse> responseObserver) {
        com.biblioteca.libros.model.Libro libro = toDomainLibro(request);
        com.biblioteca.libros.model.Libro updatedLibro = libroService.updateLibro(request.getId(), libro);
        if (updatedLibro == null) {
            responseObserver.onError(new RuntimeException("Libro no encontrado para actualización"));
            return;
        }
        LibroOuterClass.Libro grpcLibro = toGrpcLibro(updatedLibro);
        LibroOuterClass.LibroResponse response = LibroOuterClass.LibroResponse.newBuilder().setLibro(grpcLibro).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteLibro(LibroOuterClass.LibroRequest request, StreamObserver<LibroOuterClass.LibroResponse> responseObserver) {
        boolean deleted = libroService.deleteLibro(request.getId());
        if (!deleted) {
            responseObserver.onError(new RuntimeException("Libro no encontrado para eliminación"));
            return;
        }
        LibroOuterClass.LibroResponse response = LibroOuterClass.LibroResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listLibros(LibroOuterClass.Empty request, StreamObserver<LibroOuterClass.LibroListResponse> responseObserver) {
        List<com.biblioteca.libros.model.Libro> libros = libroService.getAllLibros();
        List<LibroOuterClass.Libro> grpcLibros = libros.stream()
                .map(this::toGrpcLibro)
                .collect(Collectors.toList());
        LibroOuterClass.LibroListResponse response = LibroOuterClass.LibroListResponse.newBuilder()
                .addAllLibros(grpcLibros)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}