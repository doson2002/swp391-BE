package com.example.swp.repositories;

import com.example.swp.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    @Query("SELECT p FROM Products p WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR p.productName ILIKE %:keyword%)")
    Page<Products> searchProducts(@Param("keyword") String keyword, Pageable pageable);

    List<Products> findByCounterId(Long counterId);

    void deleteByCounterId(Long counterId);

    boolean existsByBarcode(String barcode);
}
