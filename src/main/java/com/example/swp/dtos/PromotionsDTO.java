package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsDTO {
    @JsonProperty("discount_percentage")
    private double discountPercentage;
    @JsonProperty("start_date")
    private Long startDate;
    @JsonProperty("end_date")
    private Long endDate;

}
