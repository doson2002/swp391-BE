package com.example.swp.repositories;

import com.example.swp.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_Id(Long userId);

    List<Orders> findByCustomer_Id(Long customerId);

    boolean existsByCustomer_Id(Long customerId);

}
