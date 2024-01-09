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
        Cigarettes cigarette = cigarettesRepository.findById(id).get();
        updateCigarette.setId(id);
        updateCigarette.setOwner(cigarette.getOwner());
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
        return cigarettes.stream().peek(c -> {
            if (LocalDate.now().getYear() - c.getDateOfIssue().getYear() > c.getShelfLifeYear()) {
                c.setPrice(c.getPrice() / 2);
                c.setExpired(true);
            }
        }).toList();
    }

    public User getCigarettesOwner(int id) {
        return cigarettesRepository.findById(id).map(Cigarettes::getOwner).orElse(null);
    }

    public List<Cigarettes> searchByName(String query) {
        return cigarettesRepository.findByNameStartingWith(query);
    }

    @Transactional
    public void release(int id) {
        cigarettesRepository.findById(id).ifPresent(c -> c.setOwner(null));
    }

    @Transactional
    public void assign(int id, User selectedUser) {
        cigarettesRepository.findById(id).ifPresent(c -> c.setOwner(selectedUser));
    }
}
