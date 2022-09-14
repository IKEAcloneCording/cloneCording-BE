package com.innovation.backend.service;

import com.innovation.backend.dto.response.ProductListResponseDto;
import com.innovation.backend.dto.response.ProductResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Category;
import com.innovation.backend.entity.Product;
import com.innovation.backend.repository.CategoryRepository;
import com.innovation.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        int cnt = 0;
        for (Product product : productList) {
            productResponseDtoList.add(
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
            cnt++;
        }

        ProductListResponseDto productsByCategory = ProductListResponseDto.builder()
                .products(productResponseDtoList)
                .totalCount(cnt)
                .build();

        return ResponseDto.success(productsByCategory);
    }

    @Transactional
    public ResponseDto<?> searchProducts(String searchKeyword) {

        // 1. 카테고리 연관 키워드로 검색 리스트 만들기
        List<Category> relatedCategories = categoryRepository.findAllByRelatedKeywordsContaining(searchKeyword);

        List<Product> categorySearch = new ArrayList<>();
        for (Category category : relatedCategories) {
            List<Product> dataForMerge = productRepository.findAllByCategory(category);
            categorySearch.removeAll(dataForMerge);
            categorySearch.addAll(dataForMerge);
        }

        // 2. 상품 상세 설명으로 검색 리스트 만들기
        List<Product> descriptionSearch = productRepository.findAllByDescriptionContaining(searchKeyword);

        // 2. 두 리스트 병합
        List<Product> mergedList = new ArrayList<>(categorySearch);
        mergedList.removeAll(descriptionSearch);
        mergedList.addAll(descriptionSearch);

        // 3. ResponseDto로 변환하기
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        int cnt = 0;
        for (Product product : mergedList) {
            productResponseDtoList.add(
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
            cnt++;
        }

        ProductListResponseDto ProductsBySearch = ProductListResponseDto.builder()
                .products(productResponseDtoList)
                .totalCount(cnt)
                .build();

        return ResponseDto.success(ProductsBySearch);
    }
}
