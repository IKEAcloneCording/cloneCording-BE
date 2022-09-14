package com.innovation.backend.service;

import com.innovation.backend.dto.response.ProductListResponseDto;
import com.innovation.backend.crawling.JsoupCrawling;
import com.innovation.backend.dto.response.ProductResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Category;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.CategoryNotFoundException;
import com.innovation.backend.exception.EmptyValueException;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.exception.ProductNotFoundException;
import com.innovation.backend.repository.CategoryRepository;
import com.innovation.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.innovation.backend.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final JsoupCrawling jsoupCrawling;

    @Transactional
    public ResponseDto<?> showProductsByCategory(String categoryName) {

        if (categoryName == null) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);
        }

        Category selectedCategory = categoryRepository.findByName(categoryName);

        if (selectedCategory == null) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);
        }

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

        if (searchKeyword == null) {
            throw new EmptyValueException(EMPTY_VALUE);
        }

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

    public ResponseDto<?> getAllProducts() throws IOException {
        return ResponseDto.success(productRepository.findAll());
//        return ResponseDto.success(jsoupCrawling.Crawling());
    }

    public ResponseDto<?> getProduct(Long id) {
        Product product = isPresent(id);
        if(null == product) {
            throw new ProductNotFoundException(ID_NOT_FOUND);
        }
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(id)
                .name(product.getName())
                .price(product.getPrice())
                .url(product.getUrl())
                .image_url(product.getImageUrl())
                .subImage_url(product.getSubImageUrl())
                .description(product.getDescription())
                .measurement(product.getMeasurement())
                .build();
        return ResponseDto.success(productResponseDto);
    }

    public Product isPresent(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }
}
