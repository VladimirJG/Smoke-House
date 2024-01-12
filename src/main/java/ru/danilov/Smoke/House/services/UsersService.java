package ru.danilov.Smoke.House.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.repositories.CigarettesRepository;
import ru.danilov.Smoke.House.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.danilov.Smoke.House.util.Helper.hasDiscount;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final CigarettesRepository cigarettesRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, CigarettesRepository cigarettesRepository) {
        this.usersRepository = usersRepository;
        this.cigarettesRepository = cigarettesRepository;
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
        List<Cigarettes> cigarettesList = usersRepository.findById(id).get().getCigarettesList();
        updateUser.setId(id);
        updateUser.setCigarettesList(cigarettesList);
        usersRepository.save(updateUser);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public List<Cigarettes> getAllCigarettesByUser(int id) {
        Optional<User> user = usersRepository.findById(id);

        if (user.isPresent()) {
            Hibernate.initialize(user.get().getCigarettesList());

            return hasDiscount(user.get().getCigarettesList());
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<User> findUserByName(String name) {
        return usersRepository.findByName(name);
    }

    @Transactional
    public void addNewCigaretteToUser(Cigarettes selectedCigarette, int id) {
        Cigarettes ciga = cigarettesRepository.findById(selectedCigarette.getId()).orElse(null);
        User user = usersRepository.findById(id).orElse(null);
        assert user != null;
        if (user.getCigarettesList().contains(ciga))
            user.getCigarettesList().get(user.getCigarettesList().indexOf(ciga)).setCount(user.getCigarettesList().get(user.getCigarettesList().indexOf(ciga)).getCount() + 1);
        else {
            assert ciga != null;
            ciga.setCount(1);
            user.getCigarettesList().add(ciga);
        }
    }


    @Transactional
    public void putAwayCigarette(int id, int cigId) {
        Cigarettes cigarette = cigarettesRepository.findById(cigId).orElse(null);
        usersRepository.findById(id).ifPresent(c -> {
            if (c.getCigarettesList().get(c.getCigarettesList().indexOf(cigarette)).getCount() == 1)
                c.getCigarettesList().remove(cigarette);
            else
                c.getCigarettesList().get(c.getCigarettesList().indexOf(cigarette)).setCount(c.getCigarettesList().get(c.getCigarettesList().indexOf(cigarette)).getCount() - 1);
        });
    }
}

