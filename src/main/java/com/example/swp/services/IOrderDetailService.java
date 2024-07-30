package com.example.swp.services;

import com.example.swp.entities.OrderDetails;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetails> getOrderDetailsByOrderId(Long orderId);
    void updatePurchasedStatus(long orderDetailId,int purchasedStatus) throws DataNotFoundException;
    void updatePurchasedQuantity(Long orderDetailId, int purchasedQuantity) throws DataNotFoundException;
    List<OrderDetails> getReorderHistory();
}
