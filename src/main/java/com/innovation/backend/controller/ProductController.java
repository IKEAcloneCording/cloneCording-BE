package com.innovation.backend.controller;

import com.innovation.backend.dto.response.MemberResponseDto;
import com.innovation.backend.dto.response.ProductListResponseDto;
import com.innovation.backend.dto.response.ProductResponseDto;
import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name="[상품 API]")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

//카테고리 별 상품 조회
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ProductListResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
    @Operation(summary = "카테고리 별 조회", description = "카테고리 별로 상품을 조회합니다.")
    @Parameter(name = "categoryName", description = "찾고 싶은 카테고리명", in = ParameterIn.PATH)
    @RequestMapping(value = "/api/products/cat/{categoryName}", method = RequestMethod.GET)
    public ResponseDto<?> showProductsByCategory(@PathVariable String categoryName) {
        return productService.showProductsByCategory(categoryName);
    }
    // 상품 검색
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 검색 성공", content = @Content(schema = @Schema(implementation = ProductListResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
    @Operation(summary = "상품 검색", description = "상품을 검색하여 찾습니다.")
    @Parameter(name = "keyword", description = "찾고 싶은 상품명", in = ParameterIn.PATH)
    @RequestMapping(value = "/api/products/search", method = RequestMethod.GET)
    public ResponseDto<?> searchProducts(@RequestParam (value="keyword", defaultValue = "") String searchKeyword) {
        return productService.searchProducts(searchKeyword);
    }

    //상품 전체 조회
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 전체 조회 성공", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
    @Operation(summary = "상품 전체", description = "전체 상품을 조회합니다.")
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseDto<?> getAllProducts() throws IOException {
        return productService.getAllProducts();
    }

    //상품조회
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 조회 성공", content = @Content(schema = @Schema(implementation = ProductListResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorCode.class))) })
    @Operation(summary = "상품 검색", description = "상품 1개를 조회합니다.")
    @Parameter(name = "id", description = "상품의 id", in = ParameterIn.PATH)
    @RequestMapping(value = "/api/p/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }


}
