package ru.mcx73.gis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.repository.RoleRepository;

import java.util.List;


@Service
public class RoleServiceImpl {
    private List<Role> list;

    @Autowired
    RoleRepository roleRepository;

    public List<Role> AllRole() {
        list = roleRepository.findAll();
        return list;
    }

    public boolean saveUserEndRoles() {

        roleRepository.saveAndFlush(new Role(1L, "ADMIN"));
        roleRepository.saveAndFlush(new Role(2L, "MODERATOR"));
        roleRepository.saveAndFlush(new Role(3L, "USER"));


        return true;
    }



}
