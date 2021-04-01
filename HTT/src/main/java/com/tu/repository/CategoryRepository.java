package com.tu.repository;

import com.tu.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);
    Page<Category> findAllByNameContaining(String name, Pageable pageable);

    @Modifying
    @Query("update Category c set c.deleted = true where c.id = :id")
    Integer softDeleteCategory(@Param("id") Long id);

    Page<Category> findByDeletedIsFalse(Pageable pageable);

    Page<Category> findAllByDeletedIsTrue(Pageable pageable);



}
