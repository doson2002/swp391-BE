package com.example.swp.repositories;

import com.example.swp.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

    List<Customers> findByFullNameContainingIgnoreCase(String keyword);
}
