package com.example.swp.services;

import com.example.swp.dtos.CustomersDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {

    CustomersDTO addCustomer(CustomersDTO customersDTO);

    List<CustomersDTO> searchCustomers(String keyword);

    CustomersDTO updateCustomer(Long id, CustomersDTO customerDTO);

    void deleteCustomer(Long id);

}
