package com.example.swp.services;

import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Customers;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface ICustomerService {

    Customers addCustomer(CustomersDTO customersDTO);

    List<Customers> getCustomers(String keyword);
    Customers getCustomerByPhone(String phone) throws DataNotFoundException;

    Customers updateCustomer(Long id, CustomersDTO customerDTO) throws DataNotFoundException;

    void deleteCustomer(Long id) throws DataNotFoundException;

}
