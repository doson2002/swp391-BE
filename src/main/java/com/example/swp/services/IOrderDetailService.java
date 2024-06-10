package com.example.swp.services;

import com.example.swp.entities.OrderDetails;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetails> getOrderDetailsByOrderId(Long orderId);
}
