package com.example.swp.controllers;

import com.example.swp.dtos.CustomerPolicyApplicationDTO;
import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.dtos.CustomersDTO;
import com.example.swp.dtos.ProductDTO;
import com.example.swp.entities.CustomerPolicies;
import com.example.swp.entities.Products;
import com.example.swp.entities.Users;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.services.ICustomerPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer_policies")
@RequiredArgsConstructor
public class CustomerPolicyController {
    private final ICustomerPolicyService customerPolicyService;

    @PostMapping("/add_new_customer_policy")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
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
            CustomerPolicies newCustomerPolicy = customerPolicyService.createCustomerPolicy(customerPolicyDTO);

            return ResponseEntity.ok(newCustomerPolicy);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/approve_customer_policy/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> updatePublishStatusCustomerPolicy(@Valid @PathVariable Long id,
                                                   @RequestBody CustomerPolicyApplicationDTO customerPolicyApplicationDTO){
        try{
            CustomerPolicies updatePublishStatusCustomerPolicies = customerPolicyService.applyCustomerPolicy(id, customerPolicyApplicationDTO);
            return ResponseEntity.ok(updatePublishStatusCustomerPolicies);
        }catch(DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update_customer_policy/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?> updateCustomerPolicy(@Valid @PathVariable Long id,
                                                  @RequestBody CustomerPolicyDTO customerPolicyDTO){
        try{
            CustomerPolicies updateCustomerPolicy = customerPolicyService.updateCustomerPolicy(id, customerPolicyDTO);
            return ResponseEntity.ok(updateCustomerPolicy);
        }catch(DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get_policy_by_customer_and_status")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?>getPolicyByCustomerAndStatus(
            @RequestParam(required = false, defaultValue = "") Long customerId,
            @RequestParam(required = false, defaultValue = "") String publishStatus) throws DataNotFoundException {
        List<CustomerPolicies> customerPolicies =
                customerPolicyService.getAllPoliciesByCustomerIdAndStatus(customerId, publishStatus);
        return ResponseEntity.ok(customerPolicies);
    }
    @GetMapping("/get_all_customer_policies")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_STAFF')")
    public ResponseEntity<?>getAllCustomerPolicies() {
        List<CustomerPolicies> customerPolicies =
                customerPolicyService.getAllCustomerPolicies();
        return ResponseEntity.ok(customerPolicies);
    }

}
