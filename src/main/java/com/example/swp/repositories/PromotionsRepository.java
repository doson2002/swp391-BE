package com.example.swp.repositories;

import com.example.swp.entities.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionsRepository extends JpaRepository<Promotions, String> {
    Promotions findByCode(String code);
}
