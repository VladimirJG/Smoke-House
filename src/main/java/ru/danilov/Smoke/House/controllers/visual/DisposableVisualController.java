package ru.danilov.Smoke.House.controllers.visual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilov.Smoke.House.services.DisposableService;

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
}
