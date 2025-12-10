package com.web2.gestHospitalar.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        // Se já estiver autenticado, redireciona para home
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/home";
        }
        
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha inválidos!");
        }
        
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso!");
        }
        
        return "auth/login";
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("roles", auth.getAuthorities());
        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        return "auth/access-denied";
    }
}
