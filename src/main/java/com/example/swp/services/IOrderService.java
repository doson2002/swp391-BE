package com.example.swp.services;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.dtos.OrderRequestDTO;
import com.example.swp.entities.Orders;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.OrderResponse;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    Orders createOrder(List<OrderRequestDTO> orderRequests,
                       OrderDTO orderDTO) throws DataNotFoundException;
    List<Orders> getAllOrders();

    Orders getOrderById(Long id) throws DataNotFoundException;
    List<Orders> getOrdersByUserId(Long userId);

    OrderResponse getOrderResponse(Orders orders);

    List<Orders> getOrdersByCounterId(Long counterId);

    void updateOrderOrderDetail(Long orderId, OrderDTO orderDTO,
                                List<OrderRequestDTO> productsToAdd,
                                Map<Long, Integer> productsToRemove) throws DataNotFoundException ;
    void deleteOrder(Long orderId) throws DataNotFoundException;
}
