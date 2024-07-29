package com.example.swp.services;

import com.example.swp.dtos.WarrantyDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Warranty;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.OrderRepository;
import com.example.swp.repositories.WarrantyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarrantyService implements IWarrantyService {

    private final WarrantyRepository warrantyRepository;
    private final OrderRepository orderRepository;

    @Override
    public Warranty createWarranty(WarrantyDTO warrantyDTO) throws DataNotFoundException {
        Orders existingOrder = orderRepository.findById(warrantyDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find type with id"+warrantyDTO.getOrderId()));
        Warranty newWarranty = Warranty
                .builder()
                .timeWarranty(warrantyDTO.getTimeWarranty())
                .customerName(warrantyDTO.getCustomerName())
                .warrantyDetail(warrantyDTO.getWarrantyDetail())
                .order(existingOrder)
                .build();
        return warrantyRepository.save(newWarranty);
    }


}
