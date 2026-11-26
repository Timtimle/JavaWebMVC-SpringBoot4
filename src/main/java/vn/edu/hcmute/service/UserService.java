package vn.edu.hcmute.service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import vn.edu.hcmute.form.UserForm;
import vn.edu.hcmute.model.Role;
import vn.edu.hcmute.model.User;
import vn.edu.hcmute.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    public void validate(UserForm form, Long id, BindingResult errors) {
        if (!errors.hasFieldErrors("username")) {
            users.findByUsernameIgnoreCase(form.getUsername())
                    .filter(u -> !Objects.equals(u.getId(), id))
                    .ifPresent(u -> errors.rejectValue("username", "duplicate", "Username already exists."));
        }
        if (!errors.hasFieldErrors("email")) {
            users.findByEmailIgnoreCase(form.getEmail())
                    .filter(u -> !Objects.equals(u.getId(), id))
                    .ifPresent(u -> errors.rejectValue("email", "duplicate", "Email already exists."));
        }
        String password = form.getPassword();
        if (id == null || (password != null && !password.isEmpty())) {
            if (password == null || password.length() < 6 || password.getBytes(StandardCharsets.UTF_8).length > 72) {
                errors.rejectValue("password", "length", "Use at least 6 characters and at most 72 UTF-8 bytes.");
            }
        }
    }

    public User save(User user, UserForm form, Role role) {
        user.setUsername(form.getUsername().toLowerCase(Locale.ROOT));
        user.setFullName(form.getFullName().trim());
        user.setEmail(form.getEmail().toLowerCase(Locale.ROOT));
        user.setPhone(form.getPhone());
        user.setRole(role);
        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            user.setPasswordHash(encoder.encode(form.getPassword()));
        }
        return users.saveAndFlush(user);
    }

    public UserForm toForm(User user) {
        UserForm form = new UserForm();
        form.setUsername(user.getUsername());
        form.setFullName(user.getFullName());
        form.setEmail(user.getEmail());
        form.setPhone(user.getPhone());
        form.setRole(user.getRole());
        return form;
    }
}
