package com.example.swp.responses;

import com.example.swp.entities.Orders;
import com.example.swp.entities.Products;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Date date;
    @JsonProperty(value = "discount")
    private double discount;
    @JsonProperty(value = "created_by")
    private String createdBy;
    @JsonProperty(value = "customer_id")
    private Long customerId;
    @JsonProperty(value = "user_id")
    private Long userId;

    public static OrderResponse fromOrders(Orders orders) {
        OrderResponse orderResponse = OrderResponse.builder()
                .id(orders.getId())
                .createdBy(orders.getCreatedBy())
                .discount(orders.getDiscount())
                .date(orders.getDate())
                .customerId(orders.getCustomer().getId())
                .userId(orders.getUser().getId())
                .build();
        return orderResponse;
    }
}
