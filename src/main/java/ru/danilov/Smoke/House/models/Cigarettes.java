package ru.danilov.Smoke.House.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Cigarettes")
public class Cigarettes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Значение имени должно находиться в пределах от 3 до 100 символов")
    private String name;
    @Column(name = "price")
    @NotNull(message = "Цена должна быть больше 0")
    private int price;
    @Column(name = "description")
    @Size(max = 200, message = "Описание должно быть в пределах 200 символов")
    private String description;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "shelf_life_year")
    @Min(value = 1, message = "Значение должно быть не меньше 1")
    private int shelfLifeYear;
    @Column(name = "additives")
    private String additives;
    @Column(name = "strength")
    @Enumerated(EnumType.STRING)
    private Strength strength;

    public Cigarettes() {
    }

    public Cigarettes(String name, int price, String description, LocalDate dateOfIssue, int shelfLifeYear, String additives, Strength strength) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.dateOfIssue = dateOfIssue;
        this.shelfLifeYear = shelfLifeYear;
        this.additives = additives;
        this.strength = strength;
    }
}
