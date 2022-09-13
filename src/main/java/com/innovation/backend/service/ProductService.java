package com.innovation.backend.service;

import com.innovation.backend.dto.response.ProductResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.dto.response.SearchResponseDto;
import com.innovation.backend.entity.Category;
import com.innovation.backend.entity.Product;
import com.innovation.backend.repository.CategoryRepository;
import com.innovation.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseDto<?> showProductsByCategory(String categoryName) {
        Category selectedCategory = categoryRepository.findByName(categoryName);
        List<Product> productList = productRepository.findAllByCategory(selectedCategory);

        List<ProductResponseDto> catProductsResult = new ArrayList<>();

        for (Product product : productList) {
            catProductsResult.add(
                    ProductResponseDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .image_url(product.getImageUrl())
                            .subImage_url(product.getSubImageUrl())
                            .url(product.getUrl())
                            .build()
            );
        }
        return ResponseDto.success(catProductsResult);
    }

    @Transactional
    public ResponseDto<?> searchProducts(String searchKeyword) {

        // 1. 카테고리 연관 키워드로 검색 리스트 만들기
        Category relatedCategory = categoryRepository.findByRelatedKeywordsContaining(searchKeyword);
        List<Product> categorySearch = productRepository.findAllByCategory(relatedCategory);

        // 2. 상품 상세 설명으로 검색 리스트 만들기
        List<Product> descriptionSearch = productRepository.findAllByDescriptionContaining(searchKeyword);

        // 2. 두 리스트 병합
        List<Product> mergedList = new ArrayList<>(categorySearch);
        mergedList.removeAll(descriptionSearch);
        mergedList.addAll(descriptionSearch);

        // 3. ResponseDto로 변환하기
        List<ProductResponseDto> searchResultList = new ArrayList<>();

        for (Product product : mergedList) {
            searchResultList.add(
                    ProductResponseDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .image_url(product.getImageUrl())
                            .subImage_url(product.getSubImageUrl())
                            .url(product.getUrl())
                            .build()
            );
        }
        return ResponseDto.success(searchResultList);
    }
}
