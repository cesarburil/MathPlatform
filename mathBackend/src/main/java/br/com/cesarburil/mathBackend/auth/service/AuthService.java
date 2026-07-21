package br.com.cesarburil.mathBackend.auth.service;

import br.com.cesarburil.mathBackend.auth.dto.UserDto;
import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(String username) {
        return Jwts
                .builder()
                .issuer(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .claims(new HashMap<>())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode("");
        return Keys.hmacShaKeyFor(bytes);
    }


    public String register(UserDto userDto) {

        User aNewUser = User
                .builder()
                .username(userDto.getUsername())
                .build();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        aNewUser.setPassword(encoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(aNewUser);

        return savedUser.getUsername();
    }
}
