package ru.danilov.Smoke.House.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;
import ru.danilov.Smoke.House.repositories.CigarettesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CigarettesService {
    private final CigarettesRepository cigarettesRepository;

    @Autowired
    public CigarettesService(CigarettesRepository cigarettesRepository) {
        this.cigarettesRepository = cigarettesRepository;
    }

    public List<Cigarettes> allCigarettes(boolean sortByPrice) {
        if (sortByPrice)
            return discount(cigarettesRepository.findAll(Sort.by("price")));
        else
            return discount(cigarettesRepository.findAll());
    }

    public List<Cigarettes> findWithPagination(Integer page, Integer cigarettesPerPage, boolean sortByPrice) {
        if (sortByPrice)
            return discount(cigarettesRepository.findAll(PageRequest.of(page, cigarettesPerPage, Sort.by("price"))).getContent());
        else
            return discount(cigarettesRepository.findAll(PageRequest.of(page, cigarettesPerPage)).getContent());
    }

    public Cigarettes findOneCigarette(int id) {
        Optional<Cigarettes> optionalCigarettes = cigarettesRepository.findById(id);
        return optionalCigarettes.orElse(null);
    }

    @Transactional
    public void update(int id, Cigarettes updateCigarette) {
        Cigarettes cigarette = cigarettesRepository.findById(id).get();
        updateCigarette.setId(id);
        updateCigarette.setUsersList(cigarette.getUsersList());
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
            }
        }).toList();
    }

    public List<User> getCigarettesOwner(int id) {
        return cigarettesRepository.findById(id).map(Cigarettes::getUsersList).orElse(null);
    }

    public List<String> getCigarettesOwnersName(int id) {
        return Objects.requireNonNull(cigarettesRepository.findById(id).map(Cigarettes::getUsersList).orElse(null)).stream().map(User::getName).toList();
    }

    public List<Cigarettes> searchByName(String query) {
        return cigarettesRepository.findByNameStartingWith(query);
    }

}
