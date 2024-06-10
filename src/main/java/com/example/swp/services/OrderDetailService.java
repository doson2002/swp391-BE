package com.example.swp.services;

import com.example.swp.entities.OrderDetails;
import com.example.swp.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
        public List<OrderDetails> getOrderDetailsByOrderId(Long orderId) {
            return orderDetailRepository.findByOrderId(orderId);
        }
}
