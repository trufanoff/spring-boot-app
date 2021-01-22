package ru.gb.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUsersByLogin(String login);
}
