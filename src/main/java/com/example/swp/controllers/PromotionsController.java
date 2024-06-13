package com.example.swp.controllers;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Promotions;
import com.example.swp.services.IPromotionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotions")
public class PromotionsController {
    private final IPromotionsService promotionsService;

    @PostMapping("/create")
    public ResponseEntity<Promotions> createPromotions(@RequestBody PromotionsDTO promotionsDTO) {
        Promotions promotion = promotionsService.createPromotion(promotionsDTO);
        return ResponseEntity.ok(promotion);
    }


    @GetMapping("/promotions")
    public List<Promotions> getAllPromotions() {
        return promotionsService.getAllPromotions();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteExpiredPromotions() throws Exception {
        promotionsService.deleteExpiredPromotions();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/use/{code}")
    public ResponseEntity<Promotions> usePromotion(@PathVariable String code) {
        try {
            Promotions promotion = promotionsService.usePromotion(code);
            return ResponseEntity.ok(promotion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
