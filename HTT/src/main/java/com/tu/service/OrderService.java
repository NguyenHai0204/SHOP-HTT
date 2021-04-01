package com.tu.service;

import com.tu.model.Order;
import com.tu.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Page<Order> showAll(Pageable pageable);

    Optional<Order> findById(long id);

    void save(Order order);

    void delete(long id);
}
