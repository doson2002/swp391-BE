package com.example.swp.repositories;

import com.example.swp.entities.TypePrices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePricesRepository extends JpaRepository<TypePrices,Long> {
}
