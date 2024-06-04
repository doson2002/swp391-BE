package com.example.swp.repositories;

import com.example.swp.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

    @Query("SELECT c FROM Customers c WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR c.fullName ILIKE %:keyword%)")
    List<Customers> findByFullNameContainingIgnoreCase(String keyword);

    Customers findByPhone(String phone);
}
