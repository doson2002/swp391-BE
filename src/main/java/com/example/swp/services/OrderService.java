package com.example.swp.services;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.entities.*;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CustomerRepository;
import com.example.swp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService{
    private final ICustomerService customerService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    public Orders createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        Customers existingCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find customer with id"+ orderDTO.getCustomerId()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();

        Orders newOrder = Orders
                .builder()
                .date(orderDTO.getDate())
                .discount(orderDTO.getDiscount())
                .createdBy(currentUser.getFullName())
                .customer(existingCustomer)
                .build();
        return orderRepository.save(newOrder);
    }
}
