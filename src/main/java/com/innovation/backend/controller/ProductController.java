package com.innovation.backend.controller;

import com.innovation.backend.dto.response.ResponseDto;
import com.innovation.backend.entity.Product;
import com.innovation.backend.exception.ErrorCode;
import com.innovation.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseDto<?> getAllProducts() throws IOException {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/api/p/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }


}
