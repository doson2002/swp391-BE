package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypePricesDTO {
    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "buy_price_per_gram")
    private double buyPricePerGram;

    @JsonProperty(value = "sell_price_per_gram")
    private double sellPricePerGram;

    @JsonProperty(value = "type")
    private String type;


}
