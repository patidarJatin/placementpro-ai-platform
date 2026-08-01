package com.jatinpatidar.placementpro.service.jwt;

import com.jatinpatidar.placementpro.config.JwtProperties;
import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtServiceImpl implements JwtService {

    private static final String ROLE_CLAIM = "role";

    private final JwtProperties jwtProperties;

    public JwtServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIM,
                user.getRole() != null ? user.getRole().name() : null);

         return Jwts.builder()
                .subject(user.getEmail())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractEmail(String token) {
       try{
           Claims claims = Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();

           return claims.getSubject();
       }catch (ExpiredJwtException exception){
           throw  new InvalidTokenException("JWT token has expired");

       }catch (JwtException exception){
           throw  new InvalidTokenException("Invalid JWT token");
       }
    }

    @Override
    public boolean isTokenValid(String token ,User user) {
        String email = extractEmail(token);
        return email != null && email.equals(user.getEmail());
    }
}
