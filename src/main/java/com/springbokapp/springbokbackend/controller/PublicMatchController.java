package com.springbokapp.springbokbackend.controller;

import com.springbokapp.springbokbackend.model.Player;
import com.springbokapp.springbokbackend.repository.MatchRepository;
import com.springbokapp.springbokbackend.repository.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicMatchController {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public PublicMatchController(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/public-matches")
    public String viewMatches(Model model) {
        model.addAttribute("matches", matchRepository.findAll());

        List<Player> players = playerRepository.findAll();

        // Optional: log to confirm players are loading
        System.out.println("Loaded players: " + players.size());

        // Optional: remove this loop if you're now using Thymeleaf to build image paths
        for (Player player : players) {
            String encodedName = player.getName().replace(" ", "%20");
            player.setImageUrl("/images/" + encodedName + ".jpg");
        }

        model.addAttribute("players", players);
        return "user-matches";
    }
}