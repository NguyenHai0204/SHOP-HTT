package com.tu.repository;

import com.tu.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);


    Page<Product> findAllByCategoryId(Long id ,Pageable pageable);

    List<Product> findAllByCategoryId(Long id );

    @Modifying
    @Query("update Product p set p.deleted = true where p.category.id = :categoryId")
    Integer softDeletePostByCategoryId(@Param("categoryId") long categoryId);


    @Modifying
    @Query("update Product p set p.deleted = true where p.id = :id")
    Integer softDeleteProduct(@Param("id") Long id);



    @Modifying
    @Query("update Product p set p.deleted = true where p.customerCreate.id = :customerId")
    Integer softDeletePostByCustomer(@Param("customerId") long customerId);

    Page<Product> findAllByDeletedIsFalse(Pageable pageable);
    Page<Product> findAllByDeletedIsTrue(Pageable pageable);
}
