package com.example.swp.controllers;

import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.dtos.CustomersDTO;
import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.CustomerPolicies;
import com.example.swp.entities.Products;
import com.example.swp.services.ICustomerPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer_policies")
@RequiredArgsConstructor
public class CustomerPolicyController {
    private static ICustomerPolicyService customerPolicyService;

    @PostMapping("/add_new_customer_policy")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> createCustomerPolicy(
            @Valid @RequestBody CustomerPolicyDTO customerPolicyDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            CustomerPolicies newCustomerPolicy = customerPolicyService
                    .createCustomerPolicy(customerPolicyDTO);

            return ResponseEntity.ok(newCustomerPolicy);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
