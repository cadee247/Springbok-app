package com.springbokapp.springbokbackend.controller;

import com.springbokapp.springbokbackend.model.Match;
import com.springbokapp.springbokbackend.model.Player;
import com.springbokapp.springbokbackend.repository.MatchRepository;
import com.springbokapp.springbokbackend.repository.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserMatchController {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public UserMatchController(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/user/matches")
    public String upcomingMatches(Model model) {

        List<Match> matches = matchRepository.findAll();
        List<Player> players = playerRepository.findAll();

        // Dynamically assign image URLs based on player names
        for (Player player : players) {
            String encodedName = player.getName().replace(" ", "%20");
            player.setImageUrl("/images/" + encodedName + ".jpg");
        }

        model.addAttribute("matches", matches);
        model.addAttribute("players", players);

        return "user-matches";
    }
}