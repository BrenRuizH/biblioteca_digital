package com.biblioteca.gateway.configuration;

import com.biblioteca.gateway.security.JwtForwardingFilter;
import com.biblioteca.gateway.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFlux
public class GatewayConfig implements WebFluxConfigurer {

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public WebFilter jwtForwardingFilter(JwtUtils jwtUtils) {
        return new JwtForwardingFilter(jwtUtils);
    }
}
