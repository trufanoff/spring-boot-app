package ru.gb.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
