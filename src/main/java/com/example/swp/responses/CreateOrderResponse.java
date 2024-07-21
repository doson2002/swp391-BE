package com.example.swp.responses;

import com.example.swp.entities.Orders;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderResponse {
    private String message;
    private Orders order;
}
