package com.innovation.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "상품 응답DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    @Schema(description = "상품 id (PK)")
    private Long id;
    @Schema(description = "상품명")
    private String name;
    @Schema(description = "상품 상세")
    private String description;
    @Schema(description = "상품 측정 사이즈")
    private String measurement;
    @Schema(description = "상품 가격")
    private BigDecimal price;
    @Schema(description = "상품 이미지")
    private String image_url;
    @Schema(description = "상품 이미지2")
    private String subImage_url;
    @Schema(description = "url")
    private String url;
}
