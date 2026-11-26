package vn.edu.hcmute.config;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.hcmute.model.Role;
import vn.edu.hcmute.model.User;
import vn.edu.hcmute.repository.UserRepository;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner createAdmin(
            UserRepository users,
            PasswordEncoder encoder,
            @Value("${app.admin.username}") String username,
            @Value("${app.admin.password}") String password
    ) {
        return args -> {
            if (users.existsByRole(Role.ADMIN)) {
                return;
            }
            User admin = new User();
            admin.setUsername(username.toLowerCase(Locale.ROOT));
            admin.setFullName("Administrator");
            admin.setEmail("admin@example.test");
            admin.setPasswordHash(encoder.encode(password));
            admin.setRole(Role.ADMIN);
            users.save(admin);
        };
    }
}
