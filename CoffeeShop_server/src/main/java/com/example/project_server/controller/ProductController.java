package com.example.project_server.controller;

import com.example.project_server.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }
}
