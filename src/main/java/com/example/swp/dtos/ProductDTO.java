package com.example.swp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @JsonProperty(value = "product_name")
    private String productName;
    @JsonProperty(value = "barcode")
    private String barcode;
    @JsonProperty(value = "quantity")
    private int quantity;
    @JsonProperty(value = "price_processing")
    private double priceProcessing;
    @JsonProperty(value = "price_stone")
    private double priceStone;
    @JsonProperty(value = "price_rate")
    private double priceRate;
    @JsonProperty(value = "weight")
    private double weight;
    @JsonProperty(value = "weight_unit")
    private String weightUnit;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    @JsonProperty(value = "type_id")
    private Long typeId;
    @JsonProperty(value = "counter_id")
    private Long counterId;
}
