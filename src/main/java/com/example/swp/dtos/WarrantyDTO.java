package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyDTO {
    @JsonProperty(value = "time_warranty")
    private int timeWarranty;

    @JsonProperty(value = "created_date")
    private Date createdDate;

    @JsonProperty(value = "customer_name")
    private String customerName;

    @JsonProperty(value = "warranty_detail")
    private String warrantyDetail;

    @JsonProperty(value = "order_id")
    private Long orderId;
}