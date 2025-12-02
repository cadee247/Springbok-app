package com.springbokapp.springbokbackend.controller;

import com.springbokapp.springbokbackend.model.Match;
import com.springbokapp.springbokbackend.repository.MatchRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private final MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // All users can view matches
    @GetMapping("/public")
    public String getPublicMatches(Model model) {
        model.addAttribute("matches", matchRepository.findAll());
        return "public-matches"; // template for users
    }

    // Admin view with add/edit/delete
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminMatches(Model model) {
        model.addAttribute("matches", matchRepository.findAll());
        model.addAttribute("match", new Match()); // form binding
        return "matches"; // template for admin
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addMatch(@ModelAttribute Match match) {
        matchRepository.save(match);
        return "redirect:/matches";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMatch(@PathVariable Long id) {
        matchRepository.deleteById(id);
        return "redirect:/matches";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editMatch(@PathVariable Long id, Model model) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match ID:" + id));
        model.addAttribute("match", match);
        model.addAttribute("matches", matchRepository.findAll());
        return "matches"; // reuse admin template
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateMatch(@PathVariable Long id, @ModelAttribute Match match) {
        match.setId(id);
        matchRepository.save(match);
        return "redirect:/matches";
    }
}
