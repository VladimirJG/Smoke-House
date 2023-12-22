package ru.danilov.Smoke.House.controllers.visual;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.services.UsersService;
import ru.danilov.Smoke.House.util.UserValidator;

@Controller
@RequestMapping("/users")
public class UsersVisualController {

    private final UsersService usersService;
    private final UserValidator userValidator;

    @Autowired
    public UsersVisualController(UsersService usersService, UserValidator userValidator) {
        this.usersService = usersService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("allUsers", usersService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.getOneUser(id));
        model.addAttribute("cigarettes", usersService.getAllCigarettesByUser(id));
        return "users/show";

    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") @Valid User user) {
        return "users/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "users/new";
        usersService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", usersService.getOneUser(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";
        usersService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        usersService.delete(id);
        return "redirect:/users";
    }
}
