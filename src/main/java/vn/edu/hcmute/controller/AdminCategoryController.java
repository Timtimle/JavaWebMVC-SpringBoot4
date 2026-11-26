package vn.edu.hcmute.controller;

import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import vn.edu.hcmute.repository.CategoryRepository;
import vn.edu.hcmute.form.CategoryForm;
import vn.edu.hcmute.model.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryRepository categories;

    public AdminCategoryController(CategoryRepository categories) {
        this.categories = categories;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            RedirectAttributes redirect
    ) {
        q = q.trim();
        Page<Category> result = categories.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                q,
                q,
                PageRequest.of(Math.max(page, 0), 5, Sort.by("id").descending())
        );
        if (result.getNumber() > 0 && result.getNumber() >= result.getTotalPages()) {
            redirect.addAttribute("q", q);
            return "redirect:/admin/categories";
        }
        model.addAttribute("result", result);
        model.addAttribute("q", q);
        return "admin/categories/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return show(model, null);
    }

    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("categoryForm") CategoryForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash
    ) {
        return save(new Category(), form, errors, model, flash, null);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Category c = find(id);
        CategoryForm form = new CategoryForm();
        form.setName(c.getName());
        form.setDescription(c.getDescription());
        model.addAttribute("categoryForm", form);
        return show(model, id);
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @Valid @ModelAttribute("categoryForm") CategoryForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash
    ) {
        return save(find(id), form, errors, model, flash, id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            categories.delete(find(id));
            categories.flush();
            flash.addFlashAttribute("message", "Category deleted.");
        } catch (DataIntegrityViolationException ex) {
            flash.addFlashAttribute("error", "Cannot delete a category that is still in use.");
        }
        return "redirect:/admin/categories";
    }

    private String save(
            Category category,
            CategoryForm form,
            BindingResult errors,
            Model model,
            RedirectAttributes flash,
            Long id
    ) {
        if (!errors.hasFieldErrors("name")) {
            categories.findByNameIgnoreCase(form.getName().trim())
                    .filter(c -> !Objects.equals(c.getId(), id))
                    .ifPresent(c -> errors.rejectValue("name", "duplicate", "Category already exists."));
        }
        if (errors.hasErrors()) {
            return show(model, id);
        }
        category.setName(form.getName().trim());
        category.setDescription(form.getDescription());
        try {
            categories.saveAndFlush(category);
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("name", "duplicate", "Category already exists.");
            return show(model, id);
        }
        flash.addFlashAttribute("message", "Category saved.");
        return "redirect:/admin/categories";
    }

    private String show(Model model, Long id) {
        model.addAttribute("editId", id);
        return "admin/categories/form";
    }

    private Category find(Long id) {
        return categories.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
