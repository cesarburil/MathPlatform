package br.com.cesarburil.mathBackend.auth.config;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.model.UserRole;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class DemoUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public DemoUserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("demo") != null) {
            return;
        }

        User demo = User.builder()
                .username("demo")
                .password(new BCryptPasswordEncoder(12).encode("demo"))
                .role(UserRole.PREMIUM)
                .build();

        userRepository.save(demo);
    }
}
