package com.example.swp.repositories;

import com.example.swp.entities.TypePrices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypePriceRepository extends JpaRepository<TypePrices, Long> {
}
