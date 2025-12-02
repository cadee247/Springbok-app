package com.springbokapp.springbokbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private int jerseyNumber;
    private String title; // ✅ NEW FIELD for Captain, Vice-Captain, etc.

    @Transient
    private String imageUrl;

    // ✅ Required by JPA
    public Player() {}

    // ✅ Constructor for seeding with title
    public Player(String name, String role, int jerseyNumber, String title) {
        this.name = name;
        this.role = role;
        this.jerseyNumber = jerseyNumber;
        this.title = title;
    }

    // ✅ Generates a safe image filename from the player's name
    @Transient
    public String getImageFileName() {
        return name
                .trim()
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ]", "") // remove punctuation, accents, hyphens
                .replaceAll(" +", " ") // collapse multiple spaces
                .replace(" ", "%20") + ".jpg"; // encode spaces for URL
    }
}