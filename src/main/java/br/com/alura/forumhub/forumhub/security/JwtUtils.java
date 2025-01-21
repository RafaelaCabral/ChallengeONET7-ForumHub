package br.com.alura.forumhub.forumhub.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
@Component
public class JwtUtils {

    private static final String SECRET = "minha chave"; // Substitua por uma chave mais segura

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expira em 1 dia
                .signWith(SignatureAlgorithm.HS256, SECRET) // Use a chave fixa
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValido(String token) {
        try {
            extrairEmail(token);
            return true;
        } catch (Exception e) {
            return false; // Token inválido ou expirado
        }
    }
}
