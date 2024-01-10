package ru.danilov.Smoke.House.util;

import ru.danilov.Smoke.House.models.Cigarettes;

import java.time.LocalDate;
import java.util.List;

public class Helper {
    public static List<Cigarettes> hasDiscount(List<Cigarettes> cigarettes) {
        return cigarettes.stream().peek(c -> {
            if (LocalDate.now().getYear() - c.getDateOfIssue().getYear() > c.getShelfLifeYear()) {
                c.setExpired(true);
            }
        }).toList();
    }
}
