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
