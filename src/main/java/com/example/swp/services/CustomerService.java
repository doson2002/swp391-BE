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
    public List<Customers> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customers addCustomer(CustomersDTO customersDTO) throws DuplicateDataException {
        Optional<Customers> existingCustomerByEmail = customerRepository.findByEmail(customersDTO.getEmail());
        if (existingCustomerByEmail.isPresent()) {
            throw new DuplicateDataException("Email already exists: " + customersDTO.getEmail());
        }

        boolean checkExistingCustomerByPhone = customerRepository.existsByPhone(customersDTO.getPhone());
        if (checkExistingCustomerByPhone) {
            throw new DuplicateDataException("Phone number already exists: " + customersDTO.getPhone());
        }
        Customers newCustomer = Customers
                .builder()
                .fullName(customersDTO.getFullName())
                .email(customersDTO.getEmail())
                .address(customersDTO.getAddress())
                .phone(customersDTO.getPhone())
                .accumulated_point(customersDTO.getAccumulatedPoint())
                .build();
        return customerRepository.save(newCustomer);
    }

    @Override
    public List<Customers> getCustomers(String keyword) {
        return customerRepository.findByFullNameContainingIgnoreCase(keyword);
    }
    public Customers getCustomerByPhone(String phone) throws DataNotFoundException {
        Customers existingCustomer = customerRepository.findByPhone(phone);
        if (existingCustomer==null){
            throw new DataNotFoundException("Customer not found with phone number:" +phone);
        }else
            return existingCustomer;
    }

    @Override
    public Customers updateCustomer(Long id, CustomersDTO customerDTO) throws DataNotFoundException {
        Customers existingCustomer = customerRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Customer not found with id:"+ id));
        if (existingCustomer!= null) {
            existingCustomer.setFullName(customerDTO.getFullName());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setPhone(customerDTO.getPhone());
            existingCustomer.setAddress(customerDTO.getAddress());
            customerRepository.save(existingCustomer);
        }
        return null;
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

}
