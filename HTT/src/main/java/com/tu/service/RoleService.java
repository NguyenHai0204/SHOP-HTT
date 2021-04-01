package com.tu.service;

import com.tu.model.Customer;
import com.tu.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    Page<Role> showAll(Pageable pageable);

    Optional<Role> findById(long id);

    void save(Role role);

    void delete(long id);
}
