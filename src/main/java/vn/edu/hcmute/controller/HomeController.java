package vn.edu.hcmute.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.hcmute.repository.UserRepository;

@Controller
public class HomeController {
    private final UserRepository users;

    public HomeController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        users.findByUsernameIgnoreCase(principal.getName())
                .ifPresent(user -> model.addAttribute("account", user));
        return "home";
    }
}
