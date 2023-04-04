package com.fatec.starvingless.security;

import com.google.api.client.util.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${javatab.token.expiration}")
    private Long expiration;

    @Value("${javatab.token.secret:defaultSecret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            secret = "StringUsadToGenerateToken";
        }
        byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes());
        this.key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateToken(String email) {
        Long expirationValue = (expiration != null ? expiration : 180000L);
        return Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis() + expirationValue))
                .signWith(SignatureAlgorithm.HS512, key).compact();
    }
}
