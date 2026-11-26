package vn.edu.hcmute.form;

import jakarta.validation.constraints.*;
import vn.edu.hcmute.model.Role;

public class UserForm {
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9_.-]{3,40}", message = "Use 3-40 letters, digits, dots, underscores or hyphens.")
    private String username;

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @Pattern(regexp = "[0-9+ ()-]{0,20}", message = "Enter a valid phone number.")
    private String phone;

    @Size(max = 72)
    private String password;
    private Role role = Role.USER;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
