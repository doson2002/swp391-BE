package com.example.swp.controllers;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Promotions;
import com.example.swp.services.IPromotionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
public class PromotionsController {
    private final IPromotionsService promotionsService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','STAFF')")
    public ResponseEntity<Promotions> createPromotions(@RequestBody PromotionsDTO promotionsDTO) {
        Promotions promotion = promotionsService.createPromotion(promotionsDTO);
        return ResponseEntity.ok(promotion);
    }


    @GetMapping("/get_all_promotions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','STAFF')")
    public List<Promotions> getAllPromotions() {
        return promotionsService.getAllPromotions();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','STAFF')")
    public ResponseEntity<Void> deleteExpiredPromotions() throws Exception {
        promotionsService.deleteExpiredPromotions();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/use/{code}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','STAFF')")
    public ResponseEntity<Promotions> usePromotion(@PathVariable String code) {
        try {
            Promotions promotion = promotionsService.usePromotion(code);
            return ResponseEntity.ok(promotion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
