package vn.edu.hcmute.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import vn.edu.hcmute.repository.UserRepository;
import vn.edu.hcmute.service.UserService;
import vn.edu.hcmute.form.UserForm;
import vn.edu.hcmute.model.Role;
import vn.edu.hcmute.model.User;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserRepository users;
    private final UserService service;

    public AdminUserController(UserRepository users, UserService service) {
        this.users = users;
        this.service = service;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            RedirectAttributes redirect
    ) {
        q = q.trim();
        Page<User> result = users.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                q,
                q,
                q,
                PageRequest.of(Math.max(page, 0), 5, Sort.by("id").descending())
        );
        if (result.getNumber() > 0 && result.getNumber() >= result.getTotalPages()) {
            redirect.addAttribute("q", q);
            return "redirect:/admin/users";
        }
        model.addAttribute("result", result);
        model.addAttribute("q", q);
        return "admin/users/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("userForm", new UserForm());
        return show(model, null);
    }

    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("userForm") UserForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash
    ) {
        return save(new User(), form, errors, model, flash, null);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("userForm", service.toForm(find(id)));
        return show(model, id);
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("userForm") UserForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash,
            Principal principal
    ) {
        User user = find(id);
        if (user.getUsername().equalsIgnoreCase(principal.getName())) {
            if (form.getRole() != Role.ADMIN) {
                errors.rejectValue("role", "self", "You cannot change your own admin role.");
            }
            if (!user.getUsername().equalsIgnoreCase(form.getUsername())) {
                errors.rejectValue("username", "self", "You cannot change your own username.");
            }
        }
        return save(user, form, errors, model, flash, id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal, RedirectAttributes flash) {
        User user = find(id);
        if (user.getUsername().equalsIgnoreCase(principal.getName())) {
            flash.addFlashAttribute("error", "You cannot delete your own account.");
        } else {
            users.delete(user);
            flash.addFlashAttribute("message", "User deleted.");
        }
        return "redirect:/admin/users";
    }

    private String save(
            User user,
            UserForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash,
            Long id
    ) {
        service.validate(form, id, errors);
        if (form.getRole() == null) {
            errors.rejectValue("role", "required", "Select a role.");
        }
        if (errors.hasErrors()) {
            return show(model, id);
        }
        try {
            service.save(user, form, form.getRole());
        } catch (DataIntegrityViolationException ex) {
            errors.reject("duplicate", "Username or email already exists.");
            return show(model, id);
        }
        flash.addFlashAttribute("message", "User saved.");
        return "redirect:/admin/users";
    }

    private String show(Model model, Long id) {
        model.addAttribute("editId", id);
        model.addAttribute("roles", Role.values());
        return "admin/users/form";
    }

    private User find(Long id) {
        return users.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
