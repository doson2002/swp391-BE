package com.example.swp.controllers;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.dtos.OrderRequestDetailDTO;
import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Products;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.responses.OrderListResponse;
import com.example.swp.responses.OrderResponse;
import com.example.swp.responses.ProductListResponse;
import com.example.swp.responses.ProductResponse;
import com.example.swp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

//    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
//    public ResponseEntity<?> createOrder(
//            @Valid @RequestBody OrderDTO orderDTO,
//            BindingResult result){
//        try{
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            Orders newOrder = orderService.createOrder(orderDTO);
//
//            return ResponseEntity.ok(newOrder);
//        }catch(Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
@PostMapping("/create")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF')")
public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDetailDTO request,
                                          BindingResult result) {

    try {
        if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
        Orders order = orderService.createOrder(
                request.getOrderDTO().getCustomerId(),
                request.getOrderDTO().getUserId(),
                request.getOrderRequests(),
                request.getOrderDTO()
        );
        return ResponseEntity.ok(OrderResponse.fromOrders(order));
    } catch (DataNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping("/get_all_orders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF')")
    public ResponseEntity<OrderListResponse> getAllOrders() {

      List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(OrderListResponse.builder()
                .orders(orders)
                .build());
    }




}
