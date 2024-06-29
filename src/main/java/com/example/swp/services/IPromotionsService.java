package com.example.swp.services;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Promotions;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface IPromotionsService {
    Promotions createPromotion(PromotionsDTO promotionsDTO);
    List<Promotions> getAllPromotions();
    void deleteExpiredPromotions() throws Exception;
    Promotions usePromotion(String code) throws Exception;
    Promotions updatePromotion(Long id, Promotions updatedPromotion) throws DataNotFoundException;
    void deletePromotion(Long id);
}
