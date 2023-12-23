package ru.danilov.Smoke.House.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.services.UsersService;

@Component
public class UserValidator implements Validator {
    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (usersService.findUserByName(user.getName()).isPresent())
            errors.rejectValue("name","","Имя уже используется");
    }
}
