package com.biblioteca.libros.config;

import com.biblioteca.libros.service.LibroGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private LibroGrpcService libroGrpcService;

    @Bean
    public Server grpcServer() throws IOException {
        Server server = ServerBuilder.forPort(9093)
                .addService(libroGrpcService)
                .addService(ProtoReflectionService.newInstance())
                .build();
        server.start();
        return server;
    }
}
