package com.example.swp.services;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.Products;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Products createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Products updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;

    void blockOrEnable(Long id, Boolean active) throws DataNotFoundException;

    Page<ProductResponse> getAllProducts(String keyword, PageRequest pageRequest);

    Products getProduct(Long id) throws DataNotFoundException;
}
