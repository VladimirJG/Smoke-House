package ru.danilov.Smoke.House.controllers.visual;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/general")
public class General {
    @GetMapping()
    public String general() {
        return "general/general";
    }
}
