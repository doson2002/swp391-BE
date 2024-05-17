package com.example.swp.repositories;

import com.example.swp.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customers, Long> {

    List<Customers> findByFullNameContainingIgnoreCase(String keyword);
}
