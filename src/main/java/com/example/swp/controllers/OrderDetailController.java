package com.example.swp.controllers;

import com.example.swp.dtos.UpdateOrderRequestDTO;
import com.example.swp.entities.OrderDetails;
import com.example.swp.entities.Orders;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.OrderResponse;
import com.example.swp.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update_purchased_status/{orderDetailId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> updatePurchasedStatus(@Valid @PathVariable Long orderDetailId,
                                                       @RequestBody int purchasedStatus){
        try {
           orderDetailService.updatePurchasedStatus(orderDetailId, purchasedStatus);
            return ResponseEntity.ok("purchased status updated successfully.");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/update_purchased_quantity/{orderDetailId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> updatePurchasedQuantity(@Valid @PathVariable Long orderDetailId,
                                                     @RequestBody int purchasedQuantity){
        try {
            orderDetailService.updatePurchasedQuantity(orderDetailId, purchasedQuantity);
            return ResponseEntity.ok("purchased quantity updated successfully.");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/reorder-history")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<List<OrderDetails>> getReorderHistory() {
        List<OrderDetails> reorderHistory = orderDetailService.getReorderHistory();
        return ResponseEntity.ok(reorderHistory);
    }
}
