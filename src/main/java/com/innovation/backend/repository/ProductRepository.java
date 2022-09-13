package com.innovation.backend.repository;

import com.innovation.backend.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findById (Long id);
}
