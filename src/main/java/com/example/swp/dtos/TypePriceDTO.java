package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypePriceDTO {

    private Date date;
    @JsonProperty(value = "buy_price_per_gram")
    private double buyPricePerGram;

    @JsonProperty(value = "sell_price_per_gram")
    private double sellPricePerGram;

    @JsonProperty(value = "type_id")
    private Long typeId;
}
