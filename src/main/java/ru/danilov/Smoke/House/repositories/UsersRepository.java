package ru.danilov.Smoke.House.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.Smoke.House.models.Cigarettes;
import ru.danilov.Smoke.House.models.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User,Integer> {
}
