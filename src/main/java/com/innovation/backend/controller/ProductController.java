package com.innovation.backend.controller;

import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "/api/products/search", method = RequestMethod.GET)
    public ResponseDto<?> searchProducts(@RequestParam (value="keyword", defaultValue = "") String searchKeyword) {
        return productService.searchProducts(searchKeyword);
    }


}
