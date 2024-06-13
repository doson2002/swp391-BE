package com.example.swp.dtos;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDetailDTO {
    private List<OrderRequestDTO> orderRequests;
    private OrderDTO orderDTO;
}
