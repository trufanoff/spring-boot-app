package ru.gb.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
