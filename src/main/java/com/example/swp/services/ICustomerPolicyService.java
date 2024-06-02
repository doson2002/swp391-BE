package com.example.swp.services;

import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.entities.CustomerPolicies;

public interface ICustomerPolicyService {
    CustomerPolicies createCustomerPolicy(CustomerPolicyDTO customerPolicyDTO);
}
