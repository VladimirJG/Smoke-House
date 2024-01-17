package ru.danilov.Smoke.House.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.danilov.Smoke.House.exceptions.CigaretteNotCreateException;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }
        throw new CigaretteNotCreateException(errorMsg.toString());
    }
}
