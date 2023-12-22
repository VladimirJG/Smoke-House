package ru.danilov.Smoke.House.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.repositories.CigarettesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CigarettesService {
    private final CigarettesRepository cigarettesRepository;

    @Autowired
    public CigarettesService(CigarettesRepository cigarettesRepository) {
        this.cigarettesRepository = cigarettesRepository;
    }

    public List<Cigarettes> allCigarettes() {
        return discount(cigarettesRepository.findAll());
    }

    public Cigarettes findOneCigarette(int id) {
        Optional<Cigarettes> optionalCigarettes = cigarettesRepository.findById(id);
        return optionalCigarettes.orElse(null);
    }

    @Transactional
    public void update(int id, Cigarettes updateCigarette) {
        updateCigarette.setId(id);
        cigarettesRepository.save(updateCigarette);
    }

    @Transactional
    public void save(Cigarettes cigarettes) {
        cigarettesRepository.save(cigarettes);
    }

    @Transactional
    public void delete(int id) {
        cigarettesRepository.deleteById(id);
    }

    private List<Cigarettes> discount(List<Cigarettes> cigarettes) {
        cigarettes.stream().map(c -> {
            if (LocalDate.now().getYear() - c.getDateOfIssue().getYear() > c.getShelfLifeYear()) {
                c.setPrice(c.getPrice() / 2);
            }
            return c;
        }).collect(Collectors.toList());
        return cigarettes;
    }

    public Optional<User> getCigarettesOwner(int id) {
        return Optional.ofNullable(cigarettesRepository.findById(id).get().getOwner());
    }

    @Transactional
    public void release(int id) {
        cigarettesRepository.findById(id).get().setOwner(null);
    }

    @Transactional
    public void assign(int id, User selectedUser) {
        cigarettesRepository.findById(id).get().setOwner(selectedUser);
    }
}
