package com.tu.service;

import com.tu.model.Customer;
import com.tu.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> showAll(Pageable pageable);

    Optional<Product> findById(long id);

    void saves(Product product);

    void delete(long id);
}
