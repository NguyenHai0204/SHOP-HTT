package com.tu.service.impl;

import com.tu.model.Category;
import com.tu.repository.CategoryRepository;
import com.tu.repository.ProductRepository;
import com.tu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Category> showAll(Pageable pageable) {

        return categoryRepository.findByDeletedIsFalse(pageable);
    }

    @Override
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(long id) {
        boolean result = categoryRepository.softDeleteCategory(id) > 0;
        if (!result)
            throw new RuntimeException("category not found");
        productRepository.softDeletePostByCategoryId(id);
    }



    @Override
    public Page<Category> findAllByNameContaining(String s, Pageable pageable) {
        return categoryRepository.findAllByNameContaining(s, pageable);
    }


}
