package com.example.nplus1test.controller;

import com.example.nplus1test.domain.country.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CreateController {

    private final MenuService menuService;

    public CreateController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/country")
    public String countryPage() {

        return "country";
    }

    @GetMapping("/main")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "signup";
    }

    @GetMapping("/create/menu")
    public String MenuProcess(@RequestParam String menu) {

        menuService.createMenu(menu);

        return "redirect:/country";
    }

    @GetMapping("/create/ingredient")
    public String cityProcess(@RequestParam String menu, @RequestParam String ingredient) {

        menuService.createIngredient(menu, ingredient);

        return "redirect:/country";
    }

    @GetMapping("/page")
    public String testPage() {

        return "test";
    }


}
