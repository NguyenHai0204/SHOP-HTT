package com.tu.service;

import com.tu.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> showAll(Pageable pageable);

    Optional<Category> findById(long id);

    void save(Category category);

    void delete(long id);

    Page<Category> findAllByNameContaining(String s, Pageable pageable);
}
