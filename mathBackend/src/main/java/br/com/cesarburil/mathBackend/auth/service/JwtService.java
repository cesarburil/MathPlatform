package br.com.cesarburil.mathBackend.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    public String getUsernameFromToken(String token) {
        return "";
    }
}
