package com.example.swp.responses;

import com.example.swp.entities.Counters;
import com.example.swp.entities.Products;
import com.example.swp.entities.TypePrices;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    @JsonProperty(value = "product_name")
    private String productName;
    @JsonProperty(value = "barcode")
    private String barcode;
    @JsonProperty(value = "quantity")
    private Long quantity;
    @JsonProperty(value = "price_processing")
    private double priceProcessing;
    @JsonProperty(value = "price_stone")
    private double priceStone;
    @JsonProperty(value = "weight")
    private double weight;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "type")
    private TypePrices type;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    @JsonProperty(value = "counter_id")
    private Counters counter;


    public static ProductResponse fromProducts(Products products) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .barcode(products.getBarcode())
                .quantity(products.getQuantity())
                .priceProcessing(products.getPriceProcessing())
                .priceStone(products.getPriceStone())
                .weight(products.getWeight())
                .description(products.getDescription())
                .imageUrl(products.getImageUrl())
                .type(products.getType())
                .counter(products.getCounter())
                .build();
        return productResponse;
    }
}
