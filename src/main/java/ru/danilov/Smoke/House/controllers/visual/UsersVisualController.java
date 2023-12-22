package ru.danilov.Smoke.House.controllers.visual;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilov.Smoke.House.services.CigarettesService;
import ru.danilov.Smoke.House.services.UsersService;

@Controller
@RequestMapping("/users")
public class UsersVisualController {

    private final UsersService usersService;

    @Autowired
    public UsersVisualController(UsersService usersService) {
        this.usersService = usersService;
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
}
