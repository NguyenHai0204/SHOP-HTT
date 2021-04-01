package com.tu.service.impl;

import com.tu.model.Customer;
import com.tu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(userName);
        System.out.println(customer);
        if (customer == null) {
            throw new UsernameNotFoundException("user name not found");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        String role = customer.getRole().getName();
        authorities.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(customer.getEmail(),customer.getPassword(),authorities);
    }
}
