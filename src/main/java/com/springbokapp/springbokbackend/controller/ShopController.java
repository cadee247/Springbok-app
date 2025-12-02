package com.springbokapp.springbokbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {

    @GetMapping("/shop")
    public String shop(Model model) {

        model.addAttribute("items", new Item[]{
                new Item("Springbok Jersey", 59.99, "jersey.jpg"),
                new Item("Bokke Cap", 19.99, "cap.jpg"),
                new Item("Rugby Ball", 15.00, "rugbyball.jpg"),
                new Item("Bokke Scarf", 12.50, "scarf.jpg"),
                new Item("Vuvuzela", 7.99, "vuvu.jpg"),
                new Item("Poster", 9.99, "poster.jpg")
        });

        return "store";
    }

    @GetMapping("/checkout")
    public String checkout(
            @RequestParam("price") double price,
            @RequestParam(value = "name", required = false) String name,
            Model model
    ) {
        model.addAttribute("price", price);
        model.addAttribute("name", name != null ? name : "Guest");
        return "checkout";
    }


    public record Item(String name, double price, String image) {}
}
