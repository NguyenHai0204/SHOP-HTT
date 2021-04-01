package com.tu.service.impl;

import com.tu.model.Role;
import com.tu.repository.RoleRepository;
import com.tu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Page<Role> showAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Optional<Role> findById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
          roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }
}
