package com.example.swp.services;

import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.Counters;
import com.example.swp.entities.Products;
import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;

public class OrderService implements IOrderService{

    public Products createOrder(ProductDTO productDTO) throws DataNotFoundException {
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
                .weight(productDTO.getWeight())
                .quantity(productDTO.getQuantity())
                .description(productDTO.getDescription())
                .type(existingType)
                .counter(existingCounter)
                .build();
        return productRepository.save(newProduct);
    }
}
