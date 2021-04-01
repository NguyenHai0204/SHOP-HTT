package com.tu.service.impl;

import com.tu.model.Customer;
import com.tu.repository.CustomerRepository;
import com.tu.repository.ProductRepository;
import com.tu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
  @Autowired
  private ProductRepository productRepository;

    @Override
    public Page<Customer> showAll(Pageable pageable) {
        return customerRepository.findAllByDeletedIsFalse(pageable);
    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }



    @Override
    @Transactional
    public void delete(long id) {
        boolean result = customerRepository.softDeleteCustomer(id) > 0;
        if (!result)
            throw new RuntimeException("category not found");
        productRepository.softDeletePostByCustomer(id);
    }


}
