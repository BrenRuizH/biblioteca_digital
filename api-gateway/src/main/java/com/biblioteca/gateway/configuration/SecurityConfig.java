package com.biblioteca.gateway.configuration;

import com.biblioteca.gateway.security.JwtForwardingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtForwardingFilter jwtForwardingFilter) {
        http
                .csrf(CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/api/health").permitAll() // Permite acceso sin autenticación
                        .pathMatchers("/libros/**", "/revistas/**", "/usuarios/**", "/prestamos/**").authenticated() // Requiere autenticación
                        .anyExchange().denyAll() // Bloquea todas las demás solicitudes
                )
                .addFilterAt(jwtForwardingFilter, SecurityWebFiltersOrder.AUTHORIZATION); // Agrega el filtro JWT
        return http.build();
    }
}
