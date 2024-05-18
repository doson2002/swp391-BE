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
public class OrderDTO {

    private Date date;
    @JsonProperty(value = "discount")
    private double discount;
    @JsonProperty(value = "created_by")
    private String createdBy;
    @JsonProperty(value = "customer_id")
    private Long customerId;
    @JsonProperty(value = "user_id")
    private Long userId;
}
