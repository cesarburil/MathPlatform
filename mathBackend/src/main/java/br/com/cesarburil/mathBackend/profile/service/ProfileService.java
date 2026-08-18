package br.com.cesarburil.mathBackend.profile.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import br.com.cesarburil.mathBackend.infra.exception.ResourceNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.UnauthorizedException;
import br.com.cesarburil.mathBackend.profile.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public Profile getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            throw ResourceNotFoundException.of("User", currentPrincipalName);
        }
        return user.getProfile();
    }

}
