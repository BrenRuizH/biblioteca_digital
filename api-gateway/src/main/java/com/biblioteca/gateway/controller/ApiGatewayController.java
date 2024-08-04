package com.biblioteca.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGatewayController {

    @GetMapping("/health")
    public String healthCheck() {
        return "API Gateway is up and running!";
    }
}
