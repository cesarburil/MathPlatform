package br.com.cesarburil.mathBackend.auth.service;

import br.com.cesarburil.mathBackend.auth.dto.UserDto;
import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.model.UserRole;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;


    public AuthService(UserRepository userRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;

    }


    public String register(UserDto userDto) {

        UserDetails existingUser = userRepository.findByUsername(userDto.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Existing user");
        }


        User aNewUser = User
                .builder()
                .username(userDto.getUsername())
                .role(UserRole.USER)
                .build();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        aNewUser.setPassword(encoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(aNewUser);

        return savedUser.getUsername();
    }


}
