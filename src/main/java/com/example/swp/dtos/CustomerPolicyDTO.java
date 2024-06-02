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
public class CustomerPolicyDTO {
    private String description;

    @JsonProperty(value = "discount_rate")
    private double discountRate;

    @JsonProperty(value = "fixed_discount_amount")
    private Long fixedDiscountAmount;

    @JsonProperty(value = "valid_from")
    private Date validFrom;

    @JsonProperty(value = "valid_to")
    private Date validTo;

    @JsonProperty(value = "approval_required")
    private boolean approvalRequired;

    @JsonProperty(value = "customer_id")
    private Long customerId;
}
