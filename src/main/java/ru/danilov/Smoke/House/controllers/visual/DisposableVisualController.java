package ru.danilov.Smoke.House.controllers.visual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String getOneDV(@PathVariable("id") int id, Model model){
        model.addAttribute("oneDV", disposableService.findOne(id));
        List<User> usersList = disposableService.findOne(id).getUsersList();
        if (!usersList.isEmpty())
            model.addAttribute("uList", usersList);
        return "disposable_v/oneDV";
    }
}
