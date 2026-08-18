package br.com.cesarburil.mathBackend.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "admin", description = "Controller for admin-only checks")
public class AdminController {

    @GetMapping("/")
    @Operation(summary = "Admin ping", description = "Confirms that the authenticated user is an admin")
    @ApiResponse(responseCode = "200", description = "User is admin")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> helloAdmin() {
        return new ResponseEntity<>("User is admin.", HttpStatus.OK);
    }
}
