package com.example.swp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Table(name = "type_price")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TypePrices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "buy_price_per_gram")
    private double buyPricePerGram;

    @JsonProperty(value = "sell_price_per_gram")
    private double sellPricePerGram;

    @JsonProperty(value = "type")
    private String type;

}
