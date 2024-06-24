package com.example.swp.services;

import com.example.swp.dtos.CustomerPolicyApplicationDTO;
import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.entities.CustomerPolicies;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface ICustomerPolicyService {
    CustomerPolicies createCustomerPolicy(CustomerPolicyDTO customerPolicyDTO) throws DataNotFoundException;

    CustomerPolicies applyCustomerPolicy(Long id,
                                         CustomerPolicyApplicationDTO customerPolicyApplicationDTO)
            throws DataNotFoundException;
    CustomerPolicies updateCustomerPolicy(Long id,
                                          CustomerPolicyDTO customerPolicyDTO)
            throws DataNotFoundException;
    List<CustomerPolicies> getAllPoliciesByCustomerIdAndStatus(Long customerId, String publishStatus)
            throws DataNotFoundException;

    List<CustomerPolicies> getAllCustomerPolicies();
}
