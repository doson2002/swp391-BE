package com.example.swp.services;

import com.example.swp.dtos.CustomersDTO;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface ICustomerService {

    CustomersDTO addCustomer(CustomersDTO customersDTO);

    List<CustomersDTO> searchCustomers(String keyword);

    CustomersDTO updateCustomer(Long id, CustomersDTO customerDTO) throws DataNotFoundException;

    void deleteCustomer(Long id) throws DataNotFoundException;

}
