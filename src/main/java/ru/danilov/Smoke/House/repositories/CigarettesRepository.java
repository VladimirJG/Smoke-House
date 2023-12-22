package ru.danilov.Smoke.House.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.Smoke.House.models.Cigarettes;

import java.util.List;

@Repository
public interface CigarettesRepository extends JpaRepository<Cigarettes, Integer> {
    List<Cigarettes> findByNameStartingWith(String name);
}
