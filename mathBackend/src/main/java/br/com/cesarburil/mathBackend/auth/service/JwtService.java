package br.com.cesarburil.mathBackend.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {


    private CharSequence secret;

    public JwtService() throws NoSuchAlgorithmException {
        SecretKey generatedKey = generateKey();
        this.secret = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
    }

    public String generateToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .claims(new HashMap<>())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }




    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }


    private SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        return keyGen.generateKey();
    }

    public String getUsernameFromToken(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
