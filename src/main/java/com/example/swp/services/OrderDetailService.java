package com.example.swp.services;

import com.example.swp.entities.OrderDetails;
import com.example.swp.entities.Orders;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.OrderDetailRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void updatePurchasedStatus(long orderDetailId,int purchasedStatus) throws DataNotFoundException {
        OrderDetails existingOrder = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(()-> new DataNotFoundException("order not found"));
        existingOrder.setPurchasedStatus(purchasedStatus);
    }

    @Transactional
    public void updatePurchasedQuantity(Long orderDetailId, int purchasedQuantity) throws DataNotFoundException {
        OrderDetails existingOrderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(()->new DataNotFoundException("Order detail cannot find with id: "+ orderDetailId));
        int newQuantity = existingOrderDetail.getPurchasedQuantity() + purchasedQuantity;
        existingOrderDetail.setPurchasedQuantity(newQuantity);
        orderDetailRepository.save(existingOrderDetail);
    }

    @Transactional
    public List<OrderDetails> getReorderHistory() {
        return orderDetailRepository.findByPurchasedStatus(1); // Assuming 1 means 'reordered'
    }
}
