package ru.danilov.Smoke.House.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.danilov.Smoke.House.models.Strength;

import java.time.LocalDate;

@Setter
@Getter
public class CigarettesDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Значение имени должно находиться в пределах от 3 до 100 символов")
    private String name;

    @Min(value = 1, message = "Цена должна быть больше 0")
    private int price;

    @Size(max = 200, message = "Описание должно быть в пределах 200 символов")
    private String description;

    private LocalDate dateOfIssue;

    @Min(value = 1, message = "Значение должно быть не меньше 1")
    private int shelfLifeYear;

    private String additives;

    @Enumerated(EnumType.STRING)
    private Strength strength;
}
