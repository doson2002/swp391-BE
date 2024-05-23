package com.example.swp.services;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.dtos.OrderRequestDTO;
import com.example.swp.entities.*;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService{
    private final ICustomerService customerService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
//    public Orders createOrder(OrderDTO orderDTO) throws DataNotFoundException {
//        Customers existingCustomer = customerRepository.findById(orderDTO.getCustomerId())
//                .orElseThrow(()-> new DataNotFoundException("Cannot find customer with id"+ orderDTO.getCustomerId()));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Users currentUser = (Users) authentication.getPrincipal();
//
//        Orders newOrder = Orders
//                .builder()
//                .date(orderDTO.getDate())
//                .discount(orderDTO.getDiscount())
//                .createdBy(currentUser.getFullName())
//                .customer(existingCustomer)
//                .build();
//        return orderRepository.save(newOrder);
//    }
    @Transactional
    public Orders createOrder(Long customerId, Long userId, List<OrderRequestDTO> orderRequests,
                              OrderDTO orderDTO) throws DataNotFoundException {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Orders order = new Orders();
        order.setDate(new Date());
        order.setCustomer(customer);
        order.setDiscount(orderDTO.getDiscount());
        order.setUser(user);
        order.setCreatedBy(user.getUsername()); // Assuming there is a getUsername method in Users class
        order = orderRepository.save(order);

        for (OrderRequestDTO orderRequest : orderRequests) {
            Products product = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(orderRequest.getQuantity());
            orderDetail.setUnitPrice(orderRequest.getUnitPrice()); // Assuming there is a getPrice method in Products class
            orderDetailRepository.save(orderDetail);
        }

        return order;
    }
}


