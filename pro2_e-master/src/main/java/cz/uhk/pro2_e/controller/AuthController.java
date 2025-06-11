package cz.uhk.pro2_e.controller;

import cz.uhk.pro2_e.model.Role;
import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        user.setRole(Role.USER); 
        userService.saveUser(user);
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
}
