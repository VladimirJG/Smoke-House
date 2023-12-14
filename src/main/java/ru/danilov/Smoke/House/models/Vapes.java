package ru.danilov.Smoke.House.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Vapes {
    Integer id;
    private String name;
    private int price;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfIssue;
    private int shelfLifeYear;

    private String color;
    private int power;

    private int tankSize;


}
