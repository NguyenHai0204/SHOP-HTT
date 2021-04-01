package com.tu.service;

import com.tu.model.Category;
import com.tu.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {
    Page<Customer> showAll(Pageable pageable);

    Optional<Customer> findById(long id);

    void save(Customer customer);

    void delete(long id);
}
