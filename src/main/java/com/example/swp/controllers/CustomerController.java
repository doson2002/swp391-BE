package com.example.swp.controllers;


import com.example.swp.dtos.CustomersDTO;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomersDTO> addCustomer(@RequestBody CustomersDTO customerDTO) {
        CustomersDTO addedCustomer = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomersDTO>> searchCustomers(@RequestParam String keyword) {
        List<CustomersDTO> customers = customerService.searchCustomers(keyword);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomersDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomersDTO customerDTO) throws DataNotFoundException {
        CustomersDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws DataNotFoundException {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
