package com.example.swp.repositories;

import com.example.swp.entities.CustomerPolicies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicies, Long> {

}
