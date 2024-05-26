package com.example.swp.services;

import com.example.swp.dtos.PromotionsDTO;
import com.example.swp.entities.Promotions;

public interface IPromotionsService {
    Promotions createPromotion(PromotionsDTO promotionsDTO);
    void deleteExpiredPromotions() throws Exception;
    Promotions usePromotion(String code) throws Exception;
}
