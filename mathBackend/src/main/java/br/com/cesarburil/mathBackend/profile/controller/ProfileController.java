package br.com.cesarburil.mathBackend.profile.controller;

import br.com.cesarburil.mathBackend.profile.model.Profile;
import br.com.cesarburil.mathBackend.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "profile", description = "Controller to see the authenticated user profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/")
    @Operation(summary = "Get profile", description = "Return the profile of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Profile found")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @ApiResponse(responseCode = "404", description = "User or profile not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Profile> getProfile() {
        return new ResponseEntity<>(profileService.getProfile(),HttpStatus.OK);
    }

}
