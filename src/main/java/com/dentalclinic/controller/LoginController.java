package com.dentalclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Renvoie la vue "login.html" (ou "login.jsp", etc.)
    }

    
}
