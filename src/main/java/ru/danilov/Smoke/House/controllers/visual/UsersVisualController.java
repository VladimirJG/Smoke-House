package ru.danilov.Smoke.House.controllers.visual;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.services.CigarettesService;
import ru.danilov.Smoke.House.services.UsersService;
import ru.danilov.Smoke.House.util.UserValidator;

@Controller
@RequestMapping("/users")
public class UsersVisualController {

    private final UsersService usersService;
    private final UserValidator userValidator;
    private final CigarettesService cigarettesService;

    @Autowired
    public UsersVisualController(UsersService usersService, UserValidator userValidator, CigarettesService cigarettesService) {
        this.usersService = usersService;
        this.userValidator = userValidator;
        this.cigarettesService = cigarettesService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("allUsers", usersService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") int id, Model model, @ModelAttribute("cigarette") Cigarettes selectedCigarette) {
        model.addAttribute("user", usersService.getOneUser(id));
        model.addAttribute("cigarettes", usersService.getAllCigarettesByUser(id));
        model.addAttribute("allCigarettes", cigarettesService.allCigarettes(false));
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

    @PatchMapping("/{id}/new_cigarettes")
    public String addNewCigaretteToUser(@PathVariable("id") int id, @ModelAttribute("cigarette") Cigarettes selectedCigarette) {
        usersService.addNewCigaretteToUser(selectedCigarette, id);
        return "redirect:/users/" + id;
    }

    @PatchMapping("/{id}/del_cigarette")
    public String putAwayCigarette(@PathVariable("id") int id, @RequestParam(name = "cId") int cigId) {
        usersService.putAwayCigarette(id, cigId);
        return "redirect:/users/" + id;
    }
}
