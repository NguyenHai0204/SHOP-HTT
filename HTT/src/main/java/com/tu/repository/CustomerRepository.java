package com.tu.repository;

import com.tu.model.Category;
import com.tu.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {



    @Modifying
    @Query("update Customer cs set cs.deleted = true where cs.id = :id")

    Integer softDeleteCustomer(@Param("id") long id);

    Page<Customer> findAllByDeletedIsFalse(Pageable pageable);

    Page<Customer> findAllByDeletedIsTrue(Pageable pageable);

    Customer findByEmail(String email);

    Page<Customer> findByEmail(Pageable pageable, String email);
}
