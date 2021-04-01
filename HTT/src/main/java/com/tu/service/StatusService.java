package com.tu.service;

import com.tu.model.Role;
import com.tu.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StatusService {
    Page<Status> showAll(Pageable pageable);

    Optional<Status> findById(long id);

    void save(Status status);

    void delete(long id);
}
