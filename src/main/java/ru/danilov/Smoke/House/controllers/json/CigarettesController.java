package ru.danilov.Smoke.House.controllers.json;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.Smoke.House.dto.CigarettesDTO;
import ru.danilov.Smoke.House.dto.CigarettesResponse;
import ru.danilov.Smoke.House.exceptions.CigaretteNotCreateException;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.services.CigarettesService;
import ru.danilov.Smoke.House.exceptions.ErrorResponse;
import java.util.stream.Collectors;

import static ru.danilov.Smoke.House.util.ErrorsUtil.returnErrorsToClient;

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

    @GetMapping("{id}")
    public CigarettesDTO getOneCigarette(@PathVariable("id") int id) {
        return convertToCigarettesDTO(cigarettesService.findOneCigarette(id));
    }

    @GetMapping("/getByName")
    public CigarettesResponse getCigaretteByName(String name) {
        return new CigarettesResponse(cigarettesService.searchByName(name).stream()
                .map(this::convertToCigarettesDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid CigarettesDTO cigarettesDTO,
                                          BindingResult bindingResult) {
        Cigarettes cigaretteToAdd = convertToCigarette(cigarettesDTO);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        cigarettesService.save(cigaretteToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private CigarettesDTO convertToCigarettesDTO(Cigarettes cigarette) {
        return modelMapper.map(cigarette, CigarettesDTO.class);
    }

    private Cigarettes convertToCigarette(CigarettesDTO cigarettesDTO) {
        return modelMapper.map(cigarettesDTO, Cigarettes.class);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CigaretteNotCreateException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
