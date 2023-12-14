package ru.danilov.Smoke.House.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.Smoke.House.models.Cigarettes;

@Repository
public interface CigarettesRepository extends JpaRepository<Cigarettes,Integer> {
}
