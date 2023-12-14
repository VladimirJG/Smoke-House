package ru.danilov.Smoke.House.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.repositories.CigarettesRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CigarettesService {
    private final CigarettesRepository cigarettesRepository;

    @Autowired
    public CigarettesService(CigarettesRepository cigarettesRepository) {
        this.cigarettesRepository = cigarettesRepository;
    }

    public List<Cigarettes> allCigarettes() {
        return cigarettesRepository.findAll();
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
}
