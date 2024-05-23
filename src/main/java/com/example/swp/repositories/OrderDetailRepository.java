package com.example.swp.repositories;

import com.example.swp.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
}
