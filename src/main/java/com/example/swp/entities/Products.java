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

    @Column(name = "product_name")
    private String productName;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price_processing")
    private double priceProcessing;
    @Column(name = "price_stone")
    private double priceStone;
    @Column(name = "weight")
    private double weight;
    @Column(name = "weight_unit")
    private String weightUnit;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private boolean status;

    @Column(name = "price_rate")
    private double priceRate;  //tỉ lệ áp giá

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypePrices type;

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counters counter;

}
