package com.biblioteca.gateway.security;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Usa una clave secreta generada de manera segura
    private final Key secretKey = Keys.hmacShaKeyFor("my-secret-key-my-secret-key-my-secret-key".getBytes()); // Clave secreta fija (en base64 o longitud adecuada)

    // Valida el JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("JWT expired: " + e.getMessage());
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            System.out.println("JWT unsupported: " + e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("JWT malformed: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("JWT signature invalid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("JWT validation error: " + e.getMessage());
        }
        return false;
    }
}
