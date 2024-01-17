package ru.danilov.Smoke.House.controllers.json;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danilov.Smoke.House.dto.CigarettesDTO;
import ru.danilov.Smoke.House.dto.CigarettesResponse;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.services.CigarettesService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/cigarette_j")
public class CigarettesController {
    private final CigarettesService cigarettesService;
    private final ModelMapper modelMapper;

    @Autowired
    public CigarettesController(CigarettesService cigarettesService, ModelMapper modelMapper) {
        this.cigarettesService = cigarettesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public CigarettesResponse getAllCigarettes() {
        return new CigarettesResponse(cigarettesService.allCigarettes(false).stream()
                .map(this::convertToCigarettesDTO).collect(Collectors.toList()));
    }

    private CigarettesDTO convertToCigarettesDTO(Cigarettes cigarette) {
        return modelMapper.map(cigarette, CigarettesDTO.class);
    }
}
