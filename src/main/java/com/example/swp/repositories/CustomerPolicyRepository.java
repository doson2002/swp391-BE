package com.example.swp.repositories;

import com.example.swp.entities.CustomerPolicies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicies, Long> {

    @Query("SELECT cp FROM CustomerPolicies cp " +
            "WHERE (:customerId IS NULL OR cp.customer.id = :customerId) " +
            "AND (:publishingStatus IS NULL OR cp.publishingStatus = :publishingStatus "+
            "OR :publishingStatus = '')")
    List<CustomerPolicies> findByCustomerIdAndPublishingStatus(
            @Param("customerId") Long customerId,
            @Param("publishingStatus") String publishingStatus);
}
