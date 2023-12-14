package ru.danilov.Smoke.House.controllers.visual;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.services.CigarettesService;

@Controller
@RequestMapping("/cigarettes")
public class CigarettesController {

    private final CigarettesService cigarettesService;

    @Autowired
    public CigarettesController(CigarettesService cigarettesService) {
        this.cigarettesService = cigarettesService;
    }

    @GetMapping()
    public String getAllCigarettes(Model model) {
        model.addAttribute("allCigarettes", cigarettesService.allCigarettes());
        return "cigarettes/all";

    }

    @GetMapping("/{id}")
    public Cigarettes getOneCigarette(@PathVariable("id") int id) {
        return cigarettesService.findOneCigarette(id);
    }

    public void createNewCigarette(Cigarettes cigarette) {
        cigarettesService.save(cigarette);
    }

    public void update(int id, Cigarettes updateCigarette) {
        cigarettesService.update(id, updateCigarette);
    }

    public void delete() {

    }
}
