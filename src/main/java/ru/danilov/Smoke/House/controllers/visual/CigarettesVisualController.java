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

import java.util.List;

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
    public String getAllCigarettes(Model model, @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "cigarettes_Per_Page", required = false) Integer cigarettesPerPage,
                                   @RequestParam(value = "sort_By_Price", required = false) boolean sortByPrice) {
        if (page == null || cigarettesPerPage == null)
            model.addAttribute("allCigarettes", cigarettesService.allCigarettes(sortByPrice));
        else
            model.addAttribute("allCigarettes", cigarettesService.findWithPagination(page, cigarettesPerPage, sortByPrice));
        return "cigarettes/all";

    }

    @GetMapping("/{id}")
    public String getOneCigarette(@PathVariable("id") int id, Model model, @ModelAttribute("user") User user) {
        model.addAttribute("oneCigarette", cigarettesService.findOneCigarette(id));
        List<User> cigarettesOwner = cigarettesService.getCigarettesOwner(id);

        if (!cigarettesOwner.isEmpty())
            model.addAttribute("owner", cigarettesOwner);
        else
            model.addAttribute("usersAll", usersService.getAllUsers());
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

    @GetMapping("/search")
    public String searchPage() {
        return "cigarettes/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("cigarettes", cigarettesService.searchByName(query));
        return "cigarettes/search";
    }
}
