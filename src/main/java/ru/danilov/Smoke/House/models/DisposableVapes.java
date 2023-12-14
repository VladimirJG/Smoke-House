package ru.danilov.Smoke.House.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DisposableVapes {
    private Integer id;
    private String name;
    private int price;
    private String description;
    private LocalDate dateOfIssue;
    private int shelfLifeYear;
    private String color;
    private int strength;

    private int nicotineContent;

    private String taste;
    private int numberOfRods;


}
