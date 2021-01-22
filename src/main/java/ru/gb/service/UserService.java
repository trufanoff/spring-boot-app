package ru.gb.service;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.persist.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    void deleteById(Long id);
}
