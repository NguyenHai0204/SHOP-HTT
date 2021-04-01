package com.tu.service.impl;

import com.tu.model.OrderDetail;
import com.tu.repository.OrderDetailRepository;
import com.tu.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderDetailImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public Page<OrderDetail> showAll(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public Optional<OrderDetail> findById(long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public void save(OrderDetail orderDetail) {
       orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(long id) {
     orderDetailRepository.deleteById(id);
    }
}
