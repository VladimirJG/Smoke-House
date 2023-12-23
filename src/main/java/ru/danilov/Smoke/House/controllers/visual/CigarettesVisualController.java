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

import java.util.Optional;

@Controller
@RequestMapping("/cigarettes")
public class CigarettesVisualController {

    private final CigarettesService cigarettesService;
    private final UsersService usersService;

    @Autowired
    public CigarettesVisualController(CigarettesService cigarettesService, UsersService usersService) {
        this.cigarettesService = cigarettesService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String getAllCigarettes(Model model) {
        model.addAttribute("allCigarettes", cigarettesService.allCigarettes());
        return "cigarettes/all";

    }

    @GetMapping("/{id}")
    public String getOneCigarette(@PathVariable("id") int id, Model model) {
        model.addAttribute("oneCigarette", cigarettesService.findOneCigarette(id));
        User cigarettesOwner = cigarettesService.getCigarettesOwner(id);

        if (cigarettesOwner != null)
            model.addAttribute("owner", cigarettesOwner);
        else
            model.addAttribute("allUsers", usersService.getAllUsers());
        return "cigarettes/show";
    }

    @GetMapping("/new")
    public String newCigarette(@ModelAttribute("cigarette") Cigarettes cigarette) {
        return "cigarettes/new";
    }

    @PostMapping
    public String createNewCigarette(@ModelAttribute("cigarette") @Valid Cigarettes cigarette, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cigarettes/new";
        }
        cigarettesService.save(cigarette);
        return "redirect:/cigarettes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("editCigarette", cigarettesService.findOneCigarette(id));
        return "cigarettes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editCigarette") @Valid Cigarettes cigarette, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "cigarettes/edit";
        }
        cigarettesService.update(id, cigarette);
        return "redirect:/cigarettes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        cigarettesService.delete(id);
        return "redirect:/cigarettes";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        cigarettesService.release(id);
        return "redirect:/cigarettes/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("user") User selectedUser) {
        cigarettesService.assign(id, selectedUser);
        return "redirect:/cigarettes/" + id;
    }
}
