package com.example.swp.controllers;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.Products;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.OrderResponse;
import com.example.swp.responses.ProductListResponse;
import com.example.swp.responses.ProductResponse;
import com.example.swp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;


    @PostMapping(value = "/upload_products_data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<?> uploadProductsData(@RequestPart("file") MultipartFile file){

        // Kiểm tra giá trị của tham số duplicateHandle và thực hiện các hành động tương ứng
        String message = null;
        String errorMessage = null;
        List<String> uniqueBarcodes = null;
        List<String> duplicateBarcodes = null;
        try {

            Map<String, Object> result = productService.saveProductsToDatabase(file);
            // Lấy thông báo thành công và uniqueCodes từ kết quả
            message = (String) result.get("message");
            uniqueBarcodes = (List<String>) result.get("uniqueBarcodes");
            errorMessage = (String) result.get("errorMessage");
            duplicateBarcodes = (List<String>) result.get("duplicateBarcodes");

            // Trả về kết quả thành công cùng với thông báo
            Map<String, Object> responseMap = new HashMap<>();

            if (uniqueBarcodes != null) {
                String uniqueCodesMessage = message + ": " + String.join(", ", uniqueBarcodes);
                responseMap.put("Message", "Products data uploaded and saved successfully");
                responseMap.put("UniqueCodesMessage", uniqueCodesMessage);
            }
            if (duplicateBarcodes != null) {
                String duplicateCodesMessage = errorMessage + ": " + String.join(", ", duplicateBarcodes);
                responseMap.put("DuplicateCodesMessage", duplicateCodesMessage);
            }
            return ResponseEntity.ok(responseMap);
            // In ra thông báo thành công
//                return ResponseEntity.ok(Map.of("Message","Syllabuses data uploaded and saved successfully "));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        }
        // In ra thông báo thành công

    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
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
    @PutMapping("/update/{id}")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<ProductListResponse> getAllProducts(
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
    @GetMapping("/get_product_by_id/{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> getProduct(@Valid @PathVariable Long productId) throws DataNotFoundException {
        Products product = productService.getProduct(productId);
        return ResponseEntity.ok(ProductResponse.fromProducts(product));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    @GetMapping("/get_products_by_counter/{counterId}")
    public ResponseEntity<?> getProductsByCounterId(@PathVariable Long counterId) {
        List<Products> productsList = productService.getProductsByCounterId(counterId);
        List<ProductResponse> productResponses = productsList.stream()
                .map(ProductResponse::fromProducts)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }

}
