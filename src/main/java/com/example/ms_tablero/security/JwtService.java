package com.example.ms_tablero.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Valida tokens JWT firmados con HS256 (RF-06 / RNF-03).
 * Este microservicio solo VALIDA tokens; los emite ms-jugadores.
 */
@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("jwt.secret debe tener al menos 32 caracteres (HS256)");
        }
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    /** Verifica firma y expiración. Lanza JwtException si el token no es válido. */
    public Claims validar(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getExpiration() == null || claims.getSubject() == null) {
            throw new JwtException("El token debe incluir 'sub' y 'exp'");
        }
        return claims;
    }
}
