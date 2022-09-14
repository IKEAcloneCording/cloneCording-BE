package com.innovation.backend.controller;

import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "/api/products/cat/{categoryName}", method = RequestMethod.GET)
    public ResponseDto<?> showProductsByCategory(@PathVariable String categoryName) {
        return productService.showProductsByCategory(categoryName);
    }

    @RequestMapping(value = "/api/products/search", method = RequestMethod.GET)
    public ResponseDto<?> searchProducts(@RequestParam (value="keyword", defaultValue = "") String searchKeyword) {
        return productService.searchProducts(searchKeyword);
    }


}
