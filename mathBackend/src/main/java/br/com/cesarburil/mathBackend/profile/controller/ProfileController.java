package br.com.cesarburil.mathBackend.profile.controller;

import br.com.cesarburil.mathBackend.profile.model.Profile;
import br.com.cesarburil.mathBackend.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/")
    public ResponseEntity<Profile> getProfile() {
        return new ResponseEntity<>(profileService.getProfile(),HttpStatus.OK);
    }

}
