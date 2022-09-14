package com.innovation.backend.dto.response;

import com.innovation.backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDto {
    private int totalCount;
    private List<ProductResponseDto> products;
}
