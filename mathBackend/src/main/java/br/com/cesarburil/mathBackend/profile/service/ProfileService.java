package br.com.cesarburil.mathBackend.profile.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import br.com.cesarburil.mathBackend.infra.exception.AuthenticatedUserNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.ProfileNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.UserNotFoundException;
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
            throw new AuthenticatedUserNotFoundException("User is not authenticated");
        }

        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + currentPrincipalName);
        }
        if (user.getProfile() == null) {
            throw new ProfileNotFoundException("Profile not found for user: " + currentPrincipalName);
        }
        return user.getProfile();
    }

}
