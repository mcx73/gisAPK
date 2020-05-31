package ru.mcx73.gis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mcx73.gis.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();


}
