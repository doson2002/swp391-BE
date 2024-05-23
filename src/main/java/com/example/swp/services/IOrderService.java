package com.example.swp.services;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.dtos.OrderRequestDTO;
import com.example.swp.entities.Orders;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface IOrderService {
    Orders createOrder(Long customerId, Long userId, List<OrderRequestDTO> orderRequests,
                       OrderDTO orderDTO) throws DataNotFoundException;
}
