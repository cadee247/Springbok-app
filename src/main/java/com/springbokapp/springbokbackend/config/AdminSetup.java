package com.springbokapp.springbokbackend.config;

import com.springbokapp.springbokbackend.model.User;
import com.springbokapp.springbokbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSetup {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin exists
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("YourVeryUniquePassword123!")); // admin password
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);
                System.out.println("Admin user created!");
            }
        };
    }
}
