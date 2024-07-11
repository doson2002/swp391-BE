package com.example.swp.responses;

import com.example.swp.entities.Customers;
import com.example.swp.entities.OrderDetails;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Products;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private String type;
    @JsonProperty(value = "customer")
    private Customers customer;
    @JsonProperty(value = "user_id")
    private Long userId;

    private List<OrderDetails> orderDetailsList;

    public static OrderResponse fromOrders(Orders orders, List<OrderDetails> orderDetails) {
        OrderResponse orderResponse = OrderResponse.builder()
                .id(orders.getId())
                .createdBy(orders.getCreatedBy())
                .discount(orders.getDiscount())
                .type(orders.getType())
                .date(orders.getDate())
                .customer(orders.getCustomer())
                .userId(orders.getUser().getId())
                .orderDetailsList(orderDetails)
                .build();
        return orderResponse;
    }
}
