package com.example.swp.services;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Promotions;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.PromotionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionsService implements IPromotionsService{

    private final PromotionsRepository promotionsRepository;

    @Override
    public Promotions createPromotion(PromotionsDTO promotionsDTO) {
        String code = generateRandomCode(8);
        Promotions promotions = Promotions.builder()
                .code(code)
                .discountPercentage(promotionsDTO.getDiscountPercentage())
                .fixedDiscountAmount(promotionsDTO.getFixedDiscountAmount())
                .startDate(promotionsDTO.getStartDate())
                .endDate(promotionsDTO.getEndDate())
                .build();
        return promotionsRepository.save(promotions);
    }

    @Override
    public void deleteExpiredPromotions() throws Exception {
        Long now = Instant.now().getEpochSecond();
        List<Promotions> promotionsToDelete = promotionsRepository.findAll().stream()
                .filter(promotion -> promotion.getEndDate() < now || promotion.isUsed())
                .collect(Collectors.toList());
        if (promotionsToDelete.isEmpty()) {
            throw new Exception("No expired or used promotions to delete.");
        }
        promotionsRepository.deleteAll(promotionsToDelete);

    }

    @Override
    public Promotions usePromotion(String code) throws Exception {
        Promotions promotion = promotionsRepository.findById(code)
                .orElseThrow(() -> new DataNotFoundException("Promotion not found with code: " + code));
        if (promotion.isUsed()) {
            throw new Exception("Promotion code has already been used.");
        }
        promotion.setUsed(true);
        return promotionsRepository.save(promotion);
    }

    private String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code =  new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}