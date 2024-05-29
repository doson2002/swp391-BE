package com.example.swp.repositories;

import com.example.swp.entities.Orders;
import com.example.swp.entities.Products;
import com.example.swp.entities.Token;
import com.example.swp.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_Id(Long userId);

}
