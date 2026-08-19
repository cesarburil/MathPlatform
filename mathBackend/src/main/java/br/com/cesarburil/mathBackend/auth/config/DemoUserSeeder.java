package br.com.cesarburil.mathBackend.auth.config;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.model.UserRole;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DemoUserSeeder implements CommandLineRunner {

    public static final String DEMO_USERNAME = "demo";
    public static final String DEMO_PASSWORD = "demo";

    private final UserRepository userRepository;

    public DemoUserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(DEMO_PASSWORD);
        UserDetails existing = userRepository.findByUsername(DEMO_USERNAME);

        if (existing instanceof User user) {
            user.setPassword(encodedPassword);
            user.setRole(UserRole.PREMIUM);
            userRepository.save(user);
            return;
        }

        userRepository.save(User.builder()
                .username(DEMO_USERNAME)
                .password(encodedPassword)
                .role(UserRole.PREMIUM)
                .build());
    }
}
