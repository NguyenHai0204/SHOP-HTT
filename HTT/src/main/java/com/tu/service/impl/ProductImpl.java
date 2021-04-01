package com.tu.service.impl;

import com.tu.model.Product;
import com.tu.repository.ProductRepository;
import com.tu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> showAll(Pageable pageable) {
        return productRepository.findAllByDeletedIsFalse(pageable);
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void saves(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(long id) {
      productRepository.softDeleteProduct(id);
    }
}
