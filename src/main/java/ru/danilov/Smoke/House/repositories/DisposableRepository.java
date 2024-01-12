package ru.danilov.Smoke.House.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.Smoke.House.models.DisposableVapes;

@Repository
public interface DisposableRepository extends JpaRepository<DisposableVapes, Integer> {
}
