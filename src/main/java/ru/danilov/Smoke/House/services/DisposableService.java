package ru.danilov.Smoke.House.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.Smoke.House.models.DisposableVapes;
import ru.danilov.Smoke.House.repositories.DisposableRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DisposableService {
    private final DisposableRepository disposableRepository;

    @Autowired
    public DisposableService(DisposableRepository disposableRepository) {
        this.disposableRepository = disposableRepository;
    }

    public List<DisposableVapes> findAll() {
        return disposableRepository.findAll();
    }

    public DisposableVapes findOne(int id) {
        return disposableRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(DisposableVapes dv) {
        disposableRepository.save(dv);
    }

    @Transactional
    public void update(DisposableVapes upDv, int id) {
        upDv.setId(id);
        upDv.setUsersList(disposableRepository.findById(id).get().getUsersList());
        disposableRepository.save(upDv);
    }

    @Transactional
    public void delete(int id) {
        disposableRepository.deleteById(id);
    }
}
