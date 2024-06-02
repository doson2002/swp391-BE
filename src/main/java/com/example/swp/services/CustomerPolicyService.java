package com.example.swp.services;

import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.entities.CustomerPolicies;
import com.example.swp.repositories.CustomerPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerPolicyService implements ICustomerPolicyService{
    private final CustomerPolicyRepository customerPolicyRepository;

    public CustomerPolicies createCustomerPolicy(CustomerPolicyDTO customerPolicyDTO){
        CustomerPolicies newCustomerPolicy = CustomerPolicies.builder()
                .description(customerPolicyDTO.getDescription())
                .discountRate(customerPolicyDTO.getDiscountRate())
                .fixedDiscountAmount(customerPolicyDTO.getFixedDiscountAmount())
                .validFrom(customerPolicyDTO.getValidFrom())
                .validTo(customerPolicyDTO.getValidTo())
                .approvalRequired(customerPolicyDTO.isApprovalRequired())
                .build();
        return customerPolicyRepository.save(newCustomerPolicy);
    }
}
