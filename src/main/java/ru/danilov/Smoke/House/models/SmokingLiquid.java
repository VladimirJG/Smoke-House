package ru.danilov.Smoke.House.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SmokingLiquid {
    Integer id;
    private String name;
    private int price;
    private String description;
    private LocalDate dateOfIssue;
    private int shelfLifeYear;
    private int volume;
    private Strength strength;

    private int nicotineContent;

    private String taste;


}
