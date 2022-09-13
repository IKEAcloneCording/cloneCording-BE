package com.innovation.backend.repository;

import com.innovation.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByRelatedKeywordsContaining(String keyword);
}
