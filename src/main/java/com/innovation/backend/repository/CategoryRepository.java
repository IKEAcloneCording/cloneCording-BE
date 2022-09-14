package com.innovation.backend.repository;

import com.innovation.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByRelatedKeywordsContaining(String keyword);
    Optional<Category> findByName(String categoryName);
}
