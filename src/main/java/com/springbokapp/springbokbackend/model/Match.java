package com.springbokapp.springbokbackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    @Column(nullable = false)
    private String opponent;

    private String venue;

    private Integer springbokScore;

    private Integer opponentScore;

    private String result;

    // Default constructor
    public Match() {
        this.matchDate = LocalDate.now(); // default to today if not set
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Integer getSpringbokScore() { return springbokScore; }
    public void setSpringbokScore(Integer springbokScore) { this.springbokScore = springbokScore; }

    public Integer getOpponentScore() { return opponentScore; }
    public void setOpponentScore(Integer opponentScore) { this.opponentScore = opponentScore; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
