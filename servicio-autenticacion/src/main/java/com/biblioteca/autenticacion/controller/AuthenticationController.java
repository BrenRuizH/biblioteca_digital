package com.biblioteca.autenticacion.controller;

import com.biblioteca.autenticacion.model.Usuario;
import com.biblioteca.autenticacion.security.JwtUtils;
import com.biblioteca.autenticacion.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Usuario loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);

        String jwt = jwtUtils.generateJwtToken(loginRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
