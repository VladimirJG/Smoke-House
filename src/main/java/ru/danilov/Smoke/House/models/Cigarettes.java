package ru.danilov.Smoke.House.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    @ManyToMany(mappedBy = "cigarettesList")
    private List<User> usersList;
    @Column(name = "quantity")
    private Integer count;
    @Transient
    private boolean expired;

}
