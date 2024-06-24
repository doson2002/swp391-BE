package com.example.swp.services;

import com.example.swp.dtos.CustomerPolicyApplicationDTO;
import com.example.swp.dtos.CustomerPolicyDTO;
import com.example.swp.entities.CustomerPolicies;
import com.example.swp.entities.Customers;
import com.example.swp.entities.Users;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CustomerPolicyRepository;
import com.example.swp.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerPolicyService implements ICustomerPolicyService{
    private final CustomerPolicyRepository customerPolicyRepository;
    private final CustomerRepository customerRepository;

    public CustomerPolicies createCustomerPolicy(CustomerPolicyDTO customerPolicyDTO) throws DataNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();

        Customers existingCustomer = customerRepository.findById(customerPolicyDTO.getCustomerId())
                .orElseThrow(()->new DataNotFoundException("Customer not found with id: "+ customerPolicyDTO.getCustomerId()));

        // Kiểm tra nếu người dùng có vai trò là MANAGER
        boolean isManager = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"));

        CustomerPolicies newCustomerPolicy = CustomerPolicies.builder()
                .description(customerPolicyDTO.getDescription())
                .discountRate(customerPolicyDTO.getDiscountRate())
                .fixedDiscountAmount(customerPolicyDTO.getFixedDiscountAmount())
                .validFrom(customerPolicyDTO.getValidFrom())
                .validTo(customerPolicyDTO.getValidTo())
                .createdBy(currentUser.getEmail())
                .customer(existingCustomer)
                .publishingStatus(isManager ? "approved" : "pending")
                .approvalRequired(isManager ? false : true)
                .build();
        return customerPolicyRepository.save(newCustomerPolicy);
    }

    public CustomerPolicies applyCustomerPolicy(Long id,
                                                CustomerPolicyApplicationDTO customerPolicyApplicationDTO)
            throws DataNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();

        CustomerPolicies existingCustomerPolicy = customerPolicyRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("customer Policy not found with id: "+ id));
        existingCustomerPolicy.setApprovalDate(new Date());
        existingCustomerPolicy.setApprovedBy(currentUser.getEmail());
        existingCustomerPolicy.setPublishingStatus(customerPolicyApplicationDTO.getPublishingStatus());
        return customerPolicyRepository.save(existingCustomerPolicy);

    }

    public CustomerPolicies updateCustomerPolicy(Long id,
                                                CustomerPolicyDTO customerPolicyDTO)
            throws DataNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();

        CustomerPolicies existingCustomerPolicy = customerPolicyRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("customer Policy not found with id: "+ id));
        existingCustomerPolicy.setDiscountRate(customerPolicyDTO.getDiscountRate());
        existingCustomerPolicy.setDescription(customerPolicyDTO.getDescription());
        existingCustomerPolicy.setFixedDiscountAmount(customerPolicyDTO.getFixedDiscountAmount());
        existingCustomerPolicy.setValidFrom(customerPolicyDTO.getValidFrom());
        existingCustomerPolicy.setValidTo(customerPolicyDTO.getValidTo());

        return customerPolicyRepository.save(existingCustomerPolicy);

    }
    public List<CustomerPolicies> getAllPoliciesByCustomerIdAndStatus(Long customerId, String publishStatus)
            throws DataNotFoundException {
        if (customerId == null && (publishStatus == null || publishStatus.isEmpty())) {
            // Nếu cả customerId và publishStatus đều null hoặc trống
            // Trả về tất cả các chính sách ưu đãi
            return customerPolicyRepository.findAll();
        }

        if (customerId != null && !customerRepository.existsById(customerId)) {
            // Nếu customerId không null nhưng không tồn tại trong database
            throw new DataNotFoundException("Customer not found with id " + customerId);
        }

        return customerPolicyRepository.findByCustomerIdAndPublishingStatus(customerId, publishStatus);
    }

    @Override
    public List<CustomerPolicies> getAllCustomerPolicies() {
        return customerPolicyRepository.findAll();
    }
}
