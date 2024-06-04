package com.example.swp.controllers;


import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Customers;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.services.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_MANAGER')")
    public ResponseEntity<?> addCustomer(@RequestBody CustomersDTO customerDTO) {
        Customers newCustomer = customerService.addCustomer(customerDTO);
        return ResponseEntity.ok(newCustomer);
    }

    @GetMapping("/get_customers")
    public ResponseEntity<?> getCustomers(@RequestParam(required = false) String keyword) {
        try{
            List<Customers> customers = customerService.getCustomers(keyword);
            return ResponseEntity.ok(customers);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/get_customer_by_phone")
    public  ResponseEntity<?> getCustomerByPhone(@RequestParam String phone) throws DataNotFoundException {
        try{
            Customers customer = customerService.getCustomerByPhone(phone);
            return ResponseEntity.ok(customer);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody CustomersDTO customerDTO) throws DataNotFoundException {
        try{
            Customers updatedCustomer = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws DataNotFoundException {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
