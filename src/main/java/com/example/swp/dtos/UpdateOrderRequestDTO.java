package com.example.swp.dtos;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequestDTO {
    private OrderDTO orderDTO;
    private List<OrderRequestDTO> productsToAdd;
    private Map<Long, Integer> productsToRemove;

}
