package com.example.swp.services;

import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Customers;
import com.example.swp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomersDTO addCustomer(CustomersDTO customersDTO) {
        Customers customers = new Customers();
        customers.setFullName(customersDTO.getFullName());
        customers.setEmail(customersDTO.getEmail());
        customers.setPhone(customersDTO.getPhone());
        customers.setAddress(customersDTO.getAddress());

        Customers saveCustomer = customerRepository.save(customers);

        return maptoDto(saveCustomer);
    }

    @Override
    public List<CustomersDTO> searchCustomers(String keyword) {
        List<Customers> customers = customerRepository.findByFullNameContainingIgnoreCase(keyword);
        return customers.stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomersDTO updateCustomer(Long id, CustomersDTO customerDTO) {
        Optional<Customers> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customers existingCustomer = optionalCustomer.get();
            existingCustomer.setFullName(customerDTO.getFullName());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setPhone(customerDTO.getPhone());
            existingCustomer.setAddress(customerDTO.getAddress());
            Customers updatedCustomer = customerRepository.save(existingCustomer);
            return maptoDto(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customers> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    private CustomersDTO maptoDto(Customers customers){
        return new CustomersDTO(customers.getId(), customers.getFullName(), customers.getEmail(),
                customers.getPhone(), customers.getAddress());
    }
}
