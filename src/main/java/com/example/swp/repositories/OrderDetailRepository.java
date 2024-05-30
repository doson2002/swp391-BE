package com.example.swp.repositories;

import com.example.swp.entities.OrderDetails;
import com.example.swp.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
    void deleteByOrderIn(List<Orders> orders);
    List<OrderDetails> findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);
}
