package com.innovation.backend.repository;

import com.innovation.backend.entity.Category;
import com.innovation.backend.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findById (Long id);

    List<Product> findAllByCategory(Category category);
    List<Product> findAllByDescriptionContaining(String searchKeyword);

}
