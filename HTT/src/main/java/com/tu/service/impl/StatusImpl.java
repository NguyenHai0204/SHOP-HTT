package com.tu.service.impl;

import com.tu.model.Status;
import com.tu.repository.StatusRepository;
import com.tu.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;


    @Override
    public Page<Status> showAll(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    @Override
    public Optional<Status> findById(long id) {
        return statusRepository.findById(id);
    }

    @Override
    public void save(Status status) {
        statusRepository.save(status);
    }

    @Override
    public void delete(long id) {
        statusRepository.deleteById(id);
    }
}
