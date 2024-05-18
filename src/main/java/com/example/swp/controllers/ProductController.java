package com.example.swp.controllers;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.Products;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.ProductListResponse;
import com.example.swp.responses.ProductResponse;
import com.example.swp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Products newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductDTO productDTO){
        try{
            Products updateProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updateProduct);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/soft_delete_product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId,
                                                @PathVariable Boolean status) {
        try {
            productService.blockOrEnable(productId, status);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get_all_products")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "")String keyword,
            @RequestParam("page") int page, @RequestParam("limit")int limit){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("productName").ascending());
        Page<ProductResponse> productResponsePage = productService.getAllProducts(keyword, pageRequest);
        int totalPages = productResponsePage.getTotalPages();
        List<ProductResponse> productResponseList = productResponsePage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(productResponseList)
                .totalPages(totalPages)
                .build());
    }

}
