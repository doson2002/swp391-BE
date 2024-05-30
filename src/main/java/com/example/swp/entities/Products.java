package com.example.swp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean status;

    @JsonProperty(value = "price_rate")
    private double priceRate;  //tỉ lệ áp giá

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypePrices type;

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counters counter;

}
