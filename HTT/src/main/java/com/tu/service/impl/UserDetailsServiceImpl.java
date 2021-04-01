package com.tu.service.impl;

import com.tu.dto.LoginUser;
import com.tu.model.Customer;
import com.tu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer=customerRepository.findByEmail(email);
        if(customer == null ){
            throw  new UsernameNotFoundException("Không tìm thấy......");
        }

        Set<GrantedAuthority> authoritySet=new HashSet<>();
        String role=customer.getRole().getName();
        authoritySet.add(new SimpleGrantedAuthority(role));

        LoginUser user = new LoginUser();
        user.setId(customer.getId());
        user.setUsername(customer.getEmail());
        user.setPassword(customer.getPassword());
        user.addAuthority(customer.getRole().getName());


        return user;
    }
}
