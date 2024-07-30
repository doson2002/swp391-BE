package com.example.swp.repositories;

import com.example.swp.entities.Orders;
import com.example.swp.entities.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Long> {
    List<Warranty> findByOrder(Orders order);
}
