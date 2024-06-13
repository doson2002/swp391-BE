package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO
{
    @JsonProperty("product_id")
    private Long productId;
    private int quantity;

    @JsonProperty("unit_price")
    private double unitPrice;
}
