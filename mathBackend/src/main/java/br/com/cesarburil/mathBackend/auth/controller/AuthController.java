package br.com.cesarburil.mathBackend.auth.controller;

import br.com.cesarburil.mathBackend.auth.dto.UserDto;
import br.com.cesarburil.mathBackend.auth.service.AuthService;
import br.com.cesarburil.mathBackend.auth.service.JwtService;
import br.com.cesarburil.mathBackend.auth.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDto.getUsername());
        }

        throw new RuntimeException("Not authenticated");

    }

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }



}
