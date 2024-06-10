package com.example.swp.controllers;

import com.example.swp.entities.OrderDetails;
import com.example.swp.entities.Orders;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.OrderResponse;
import com.example.swp.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;

    @GetMapping("/get_order_detail_by_order_id/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> getOrder(@Valid @PathVariable Long orderId) throws DataNotFoundException {
        List<OrderDetails> orderDetailsList = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetailsList);
    }
}
