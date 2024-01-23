package ru.danilov.Smoke.House.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {
    private String message;
    private LocalDateTime date;
}
