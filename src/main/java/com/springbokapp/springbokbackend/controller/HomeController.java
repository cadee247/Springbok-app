package com.springbokapp.springbokbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Map both "/" and "/home" to the home.html template
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // Spring looks for home.html in src/main/resources/templates
    }
}
