package com.springbokapp.springbokbackend;

import com.springbokapp.springbokbackend.model.Player;
import com.springbokapp.springbokbackend.model.User;
import com.springbokapp.springbokbackend.repository.PlayerRepository;
import com.springbokapp.springbokbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main entry point for the Springbok Backend REST API.
 */
@SpringBootApplication
public class SpringbokBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbokBackendApplication.class, args);
    }

    /**
     * Seeds the database with Springbok players if none exist.
     */
    @Bean
    public CommandLineRunner seedPlayers(PlayerRepository playerRepository) {
        return args -> {
            if (playerRepository.count() == 0) {
                playerRepository.save(new Player("Bongi Mbonambi", "Hooker", 2, "Vice-Captain"));
                playerRepository.save(new Player("Cheslin Kolbe", "Wing", 11, "Finisher"));
                playerRepository.save(new Player("Siya Kolisi", "Flanker", 6, "Captain"));
                playerRepository.save(new Player("Handré Pollard", "Fly-Half", 10, "Playmaker"));
                playerRepository.save(new Player("Eben Etzebeth", "Lock", 4, "Enforcer"));
                playerRepository.save(new Player("Canan Moodie", "Wing", 13, "Rising Star"));
                playerRepository.save(new Player("Damian de Allende", "Centre", 12, "Midfield Anchor"));
                playerRepository.save(new Player("Faf de Klerk", "Scrum-Half", 9, "Tactical Leader"));
                playerRepository.save(new Player("Frans Malherbe", "Prop", 3, "Scrum Anchor"));
                playerRepository.save(new Player("Jasper Wiese", "Number Eight", 8, "Ball Carrier"));
                playerRepository.save(new Player("Kurt-Lee Arendse", "Wing", 14, "Speedster"));
                playerRepository.save(new Player("Kwagga Smith", "Flanker", 7, "Breakdown Specialist"));
                playerRepository.save(new Player("Lukhanyo Am", "Centre", 13, "Defensive Leader"));
                playerRepository.save(new Player("Makazole Mapimpi", "Wing", 11, "Try Machine"));
                playerRepository.save(new Player("Malcolm Marx", "Hooker", 2, "Impact Sub"));
                playerRepository.save(new Player("Manie Libbok", "Fly-Half", 10, "Kicking Specialist"));
                playerRepository.save(new Player("Ox Nche", "Prop", 1, "Fan Favourite"));
                playerRepository.save(new Player("Pieter-Steph du Toit", "Flanker", 7, "Lineout Leader"));
                playerRepository.save(new Player("RG Snyman", "Lock", 5, "Towering Presence"));
                playerRepository.save(new Player("Steven Kitshoff", "Prop", 1, "Scrum Beast"));
                playerRepository.save(new Player("Willie le Roux", "Fullback", 15, "Backline General"));
            }
        };
    }

    /**
     * Seeds the database with an admin and a regular user if they do not exist.
     */
    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Admin user
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            // Regular user
            if (userRepository.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }
}
