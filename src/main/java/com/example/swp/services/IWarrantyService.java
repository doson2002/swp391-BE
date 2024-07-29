package com.example.swp.services;

import com.example.swp.dtos.WarrantyDTO;
import com.example.swp.entities.Warranty;
import com.example.swp.exceptions.DataNotFoundException;

public interface IWarrantyService {
    Warranty createWarranty(WarrantyDTO warrantyDTO) throws DataNotFoundException;
}
