package com.tu.service;

import com.tu.model.Order;
import com.tu.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderDetailService {
    Page<OrderDetail> showAll(Pageable pageable);

    Optional<OrderDetail> findById(long id);

    void save(OrderDetail orderDetail);

    void delete(long id);
}
