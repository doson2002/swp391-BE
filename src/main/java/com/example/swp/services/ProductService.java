package com.example.swp.services;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.dtos.TypePriceDTO;
import com.example.swp.entities.Counters;
import com.example.swp.entities.Products;
import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CounterRepository;
import com.example.swp.repositories.ProductRepository;
import com.example.swp.repositories.TypePricesRepository;
import com.example.swp.responses.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{
    private final CounterRepository counterRepository;
    private final ProductRepository productRepository;
    private final TypePricesRepository typePricesRepository;
    private final ExcelUploadService excelUploadService;


    @Transactional
    public Map<String, Object> saveProductsToDatabase(MultipartFile file) throws DataNotFoundException{
        Map<String, Object> result = new HashMap<>();
        if(excelUploadService.isValidExcelFile(file)) {
            try {
                List<Products> productsList = excelUploadService.getProductsDataFromExcel(file.getInputStream());
                List<Products>  productsToSave = new ArrayList<>();
                for (Products product : productsList) {
                    boolean barcodeExists = false;
                    barcodeExists = productRepository.existsByBarcode(product.getBarcode());
                    if(!barcodeExists){
                        productsToSave.add(product);
                    }
                    this.productRepository.saveAll(productsToSave);
                }
                // Thông báo về các mã code trùng lặp nếu cần
                List<String> duplicateBarcodes = productsList.stream()
                        .map(Products::getBarcode)
                        .filter(barcode -> productsToSave.stream().noneMatch(s -> s.getBarcode().equals(barcode)))
                        .distinct()
                        .collect(Collectors.toList());
                List<String> uniqueBarcodes = productsList.stream()
                        .map(Products::getBarcode)
                        .filter(barcode -> !duplicateBarcodes.contains(barcode)) // Loại bỏ các mã đã được xác định là trùng lặp
                        .distinct()
                        .collect(Collectors.toList());
                // Thêm thông báo thành công và uniqueCodes vào kết quả
                result.put("message", "Update successfully with unique codes");
                result.put("uniqueBarcodes", uniqueBarcodes);
                result.put("errorMessage", "Duplicate codes found in the Excel file: ");
                result.put("duplicateBarcodes",duplicateBarcodes);
                if (uniqueBarcodes.isEmpty()){
                    throw new DataNotFoundException("No rows were imported successfully, please check the data in the excel file again.");
                }
                return result;
            }catch (IOException e){
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
        return result;

    }
    public Products createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Counters existingCounter = counterRepository.findById(productDTO.getCounterId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find counter id"+ productDTO.getCounterId()));
        TypePrices existingType = typePricesRepository.findById(productDTO.getTypeId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find type with id"+productDTO.getTypeId()));
        Products newProduct = Products
                .builder()
                .productName(productDTO.getProductName())
                .barcode(productDTO.getBarcode())
                .priceProcessing(productDTO.getPriceProcessing())
                .priceStone(productDTO.getPriceStone())
                .priceRate(productDTO.getPriceRate())
                .weight(productDTO.getWeight())
                .weightUnit(productDTO.getWeightUnit())
                .quantity(productDTO.getQuantity())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .type(existingType)
                .counter(existingCounter)
                .build();
        return productRepository.save(newProduct);
    }
    @Override
    public Products updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Product cannot find with id"+ id));
        if(existingProduct!=null){
            Counters existingCounter =
                    counterRepository.findById(productDTO.getCounterId())
                            .orElseThrow(()->
                                    new DataNotFoundException("cannot find counter with id"+ productDTO.getCounterId()));
            TypePrices existingType = typePricesRepository.findById(productDTO.getTypeId())
                    .orElseThrow(()-> new DataNotFoundException("Cannot find type with id"+productDTO.getTypeId()));
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setBarcode(productDTO.getBarcode());
            existingProduct.setPriceProcessing(productDTO.getPriceProcessing());
            existingProduct.setPriceStone(productDTO.getPriceStone());
            existingProduct.setPriceRate(productDTO.getPriceRate());
            existingProduct.setType(existingType);
            existingProduct.setWeight(productDTO.getWeight());
            existingProduct.setWeightUnit(productDTO.getWeightUnit());
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setImageUrl(productDTO.getImageUrl());
            existingProduct.setCounter(existingCounter);
            return productRepository.save(existingProduct);
        }
        return null;
    }
    public void blockOrEnable(Long id, Boolean active) throws DataNotFoundException {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("product not found"));
        existingProduct.setStatus(active);
        productRepository.save(existingProduct);
    }
    public Page<ProductResponse>getAllProducts(String keyword, PageRequest pageRequest) {
        Page<Products> productPage = productRepository.searchProducts(keyword, pageRequest);
        return productPage.map(ProductResponse::fromProducts);
    }
    public Products getProduct(Long id) throws DataNotFoundException {
        return productRepository.findById(id).orElseThrow(()->new DataNotFoundException("Product not found with id:" + id));
    }
    public List<Products> getProductsByCounterId(Long counterId) {
        return productRepository.findByCounterId(counterId);
    }


}
