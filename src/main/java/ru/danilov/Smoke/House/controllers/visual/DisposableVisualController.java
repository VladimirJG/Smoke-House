package ru.danilov.Smoke.House.controllers.visual;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.DisposableVapes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.services.DisposableService;

import java.util.List;

@Controller
@RequestMapping("/disposable_v")
public class DisposableVisualController {

    private final DisposableService disposableService;

    @Autowired
    public DisposableVisualController(DisposableService disposableService) {
        this.disposableService = disposableService;
    }

    @GetMapping()
    public String getAllDV(Model model) {
        model.addAttribute("DV", disposableService.findAll());
        return "disposable_v/all";
    }

    @GetMapping("/{id}")
    public String getOneDV(@PathVariable("id") int id, Model model) {
        model.addAttribute("oneDV", disposableService.findOne(id));
        List<User> usersList = disposableService.findOne(id).getUsersList();
        if (!usersList.isEmpty())
            model.addAttribute("uList", usersList);
        return "disposable_v/oneDV";
    }

    @GetMapping("/newDV")
    public String newDV(@ModelAttribute("dVape") DisposableVapes vape) {
        return "disposable_v/newDV";
    }

    @PostMapping
    public String createNewDV(@ModelAttribute("dVape") @Valid DisposableVapes vape, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "disposable_v/newDV";
        }
        disposableService.save(vape);
        return "redirect:/disposable_v";
    }

}
