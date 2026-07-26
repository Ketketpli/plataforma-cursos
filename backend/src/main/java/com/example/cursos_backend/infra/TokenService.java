package com.example.cursos_backend.infra;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.cursos_backend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.time.Instant;


@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("cursos-backend") // identifica quem emitiu o token
                    .withSubject(user.getEmail()) // "dono" do token
                    .withClaim("role", user.getRole().name()) // informação extra
                    .withExpiresAt(generateExpirationDate()) // data de expiração
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("cursos-backend")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationDate() {
        return Instant.now().plusMillis(expirationTime);
    }
}
