package br.com.cesarburil.mathBackend.auth.service;

import br.com.cesarburil.mathBackend.auth.dto.UserDto;
import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(String username) {
        return "";
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
