package br.com.cesarburil.mathBackend.auth.controller;

import br.com.cesarburil.mathBackend.auth.dto.UserDto;
import br.com.cesarburil.mathBackend.auth.service.AuthService;
import br.com.cesarburil.mathBackend.auth.service.JwtService;
import br.com.cesarburil.mathBackend.infra.exception.InvalidCredentialsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth", description = "Controller to register and authenticate users")
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
    @Operation(summary = "Login", description = "Authenticate user and return a JWT")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid username or password")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(userDto.getUsername()), HttpStatus.OK);
        }

        throw new InvalidCredentialsException("Invalid username or password");

    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Create a new user account")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.register(userDto), HttpStatus.CREATED);
    }

}
