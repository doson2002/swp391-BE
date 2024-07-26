package com.example.swp.services;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Orders;
import com.example.swp.entities.Promotions;
import com.example.swp.entities.Token;
import com.example.swp.entities.Users;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.PromotionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public List<Promotions> getAllPromotions() {

        return promotionsRepository.findAll();
    }

    @Override
    public void deleteExpiredPromotions() throws Exception {
        Date now = new Date();
        List<Promotions> promotionsToDelete = promotionsRepository.findAll().stream()
                .filter(promotion -> promotion.getEndDate().before(now))
                .collect(Collectors.toList());
        if (promotionsToDelete.isEmpty()) {
            throw new Exception("No expired or used promotions to delete.");
        }
        promotionsRepository.deleteAll(promotionsToDelete);
    }

    @Transactional
    public void deletePromotionById(Long promotionId) throws DataNotFoundException {
        Promotions promotions = promotionsRepository.findById(promotionId)
                        .orElseThrow(()-> new DataNotFoundException("Cannot found promotion with id: "+ promotionId));
        promotionsRepository.delete(promotions);
    }

    @Override
    public Promotions getPromotionByCode(String code) throws Exception {
        return promotionsRepository.findByCode(code);
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
