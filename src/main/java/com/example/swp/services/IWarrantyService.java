package com.example.swp.services;

import com.example.swp.dtos.WarrantyDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Warranty;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface IWarrantyService {
    Warranty createWarranty(WarrantyDTO warrantyDTO) throws DataNotFoundException;
    Warranty getWarrantyById(Long id);
    List<Warranty> getDetailsByOrder(Orders order);
}
