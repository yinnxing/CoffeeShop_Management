package com.example.project_server.service;
import com.example.project_server.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
     List<Product> getAllProduct();
}
