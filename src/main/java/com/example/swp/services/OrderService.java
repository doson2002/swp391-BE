package com.example.swp.services;

import com.example.swp.dtos.OrderDTO;
import com.example.swp.dtos.OrderRequestDTO;
import com.example.swp.entities.*;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Orders createOrder(List<OrderRequestDTO> orderRequests,
                              OrderDTO orderDTO) throws DataNotFoundException {
        Customers customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        Users user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Orders order = new Orders();
        order.setDate(new Date());
        order.setCustomer(customer);
        order.setDiscount(orderDTO.getDiscount());
        order.setType(orderDTO.getType());
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
    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) throws DataNotFoundException {
        return orderRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find Order with id: "+ id));
    }

    public List<Orders> getOrdersByUserId(Long userId){
        return orderRepository.findByUser_Id(userId);
    }

    @Transactional
    public void updateOrderOrderDetail(Long orderId, OrderDTO orderDTO,
                                       List<OrderRequestDTO> productsToAdd,
                                       Map<Long, Integer> productsToRemove) throws DataNotFoundException {
        updateOrder(orderId, orderDTO);
        updateOrderDetailByOrderId(orderId, productsToAdd, productsToRemove);
    }

    private void updateOrderDetailByOrderId(Long orderId,
                                           List<OrderRequestDTO> productsToAdd,
                                           Map<Long, Integer> productsToRemove) throws DataNotFoundException {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        // Xử lý thêm sản phẩm từ danh sách OrderRequestDTO
        for (OrderRequestDTO productToAdd : productsToAdd) {
            Products product = productRepository.findById(productToAdd.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            // Kiểm tra xem sản phẩm đã tồn tại trong chi tiết đơn hàng chưa
            OrderDetails existingOrderDetail = orderDetailRepository.findByOrderId(orderId).stream()
                    .filter(detail -> detail.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);
            // Nếu sản phẩm chưa tồn tại trong chi tiết đơn hàng, thêm mới
            if (existingOrderDetail==null) {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(productToAdd.getQuantity());
                orderDetail.setUnitPrice(productToAdd.getUnitPrice());

                orderDetailRepository.save(orderDetail);
            } else {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                existingOrderDetail.setQuantity(existingOrderDetail.getQuantity() + productToAdd.getQuantity());
            }

            // Xử lý xóa sản phẩm từ danh sách productsToRemove
            for (Map.Entry<Long, Integer> entry : productsToRemove.entrySet()) {
                Long productIdToRemove = entry.getKey();
                Integer quantityToRemove = entry.getValue();

                // Tìm chi tiết đơn hàng tương ứng với sản phẩm cần xóa
                Optional<OrderDetails> orderDetailOptional = orderDetailRepository.findByOrderId(orderId).stream()
                        .filter(detail -> detail.getProduct().getId().equals(productIdToRemove))
                        .findFirst();

                // Nếu sản phẩm tồn tại trong đơn hàng, cập nhật số lượng hoặc xóa nếu số lượng còn lại là 0
                if (orderDetailOptional.isPresent()) {
                    OrderDetails orderDetail = orderDetailOptional.get();
                    int remainingQuantity = orderDetail.getQuantity() - quantityToRemove;
                    if (remainingQuantity > 0) {
                        orderDetail.setQuantity(remainingQuantity);
                    } else {
                        orderDetailRepository.delete(orderDetail);
                    }
                }
            }
        }
    }
    private void updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(()->new DataNotFoundException("Order cannot find with id: "+ orderId));
        Customers existingCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(()-> new DataNotFoundException("Customer cannot find with id:"+ orderDTO.getCustomerId()));
        //Update Order
        existingOrder.setDiscount(orderDTO.getDiscount());
        existingOrder.setDate(orderDTO.getDate());
        existingOrder.setType(orderDTO.getType());
        existingOrder.setCustomer(existingCustomer);
        existingOrder.setUser(existingOrder.getUser());
        orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId) throws DataNotFoundException {
        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(()->new DataNotFoundException("Order cannot find with id: "+ orderId));
        orderDetailRepository.deleteByOrderId(orderId);
        orderRepository.delete(existingOrder);
    }


}


