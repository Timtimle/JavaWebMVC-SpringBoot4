package vn.edu.hcmute.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import vn.edu.hcmute.form.UserForm;
import vn.edu.hcmute.model.Role;
import vn.edu.hcmute.model.User;
import vn.edu.hcmute.service.UserService;

@Controller
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userForm") UserForm form, BindingResult errors) {
        service.validate(form, null, errors);
        if (errors.hasErrors()) {
            return "register";
        }
        try {
            service.save(new User(), form, Role.USER);
        } catch (DataIntegrityViolationException ex) {
            errors.reject("duplicate", "Username or email already exists.");
            return "register";
        }
        return "redirect:/login?registered";
    }
}
