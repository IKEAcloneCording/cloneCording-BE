package com.innovation.backend.repository;

import com.innovation.backend.entity.Category;
import com.innovation.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);
    List<Product> findAllByDescriptionContaining(String searchKeyword);
    Optional<Product> findById(Long id);
}
