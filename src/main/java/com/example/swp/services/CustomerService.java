package com.example.swp.services;

import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Customers;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomersDTO addCustomer(CustomersDTO customersDTO) {
        Customers customers = new Customers();
        customers.setFullName(customersDTO.getFullName());
        customers.setEmail(customersDTO.getEmail());
        customers.setPhone(customersDTO.getPhone());
        customers.setAddress(customersDTO.getAddress());

        Customers savedCustomer = customerRepository.save(customers);
        return mapToDto(savedCustomer);
    }

    @Override
    public List<CustomersDTO> searchCustomers(String keyword) {
        List<Customers> customers = customerRepository.findByFullNameContainingIgnoreCase(keyword);
        return customers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomersDTO updateCustomer(Long id, CustomersDTO customerDTO) throws DataNotFoundException {
        Optional<Customers> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customers existingCustomer = optionalCustomer.get();
            existingCustomer.setFullName(customerDTO.getFullName());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setPhone(customerDTO.getPhone());
            existingCustomer.setAddress(customerDTO.getAddress());
            Customers updatedCustomer = customerRepository.save(existingCustomer);
            return mapToDto(updatedCustomer);
        } else {
            throw new DataNotFoundException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteCustomer(Long id) throws DataNotFoundException  {
        Optional<Customers> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Customer not found with id: " + id);
        }
    }

    private CustomersDTO mapToDto(Customers customers) {
        return new CustomersDTO(customers.getId(), customers.getFullName(), customers.getEmail(),
                customers.getPhone(), customers.getAddress());
    }
}
