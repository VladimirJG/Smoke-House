package ru.danilov.Smoke.House.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getOneUser(int id) {
        Optional<User> user = usersRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updateUser) {
        updateUser.setId(id);
        usersRepository.save(updateUser);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    public List<Cigarettes> getAllCigarettesByUser(int id) {
        Optional<User> user = usersRepository.findById(id);

        if (user.isPresent())
            return user.get().getCigarettesList();

        return Collections.emptyList();
    }

    public Optional<User> findUserByName(String name) {
        return Optional.ofNullable(usersRepository.findAll()
                .stream().filter(u -> u.getName().equals(name)).toList().get(0));
    }
}
