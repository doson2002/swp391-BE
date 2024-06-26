package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsDTO {
    @JsonProperty("discount_percentage")
    private double discountPercentage;
    @JsonProperty(value = "fixed_discount_amount")
    private Long fixedDiscountAmount;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;

}
