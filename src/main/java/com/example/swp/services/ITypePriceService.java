package com.example.swp.services;

import com.example.swp.dtos.TypePricesDTO;
import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ITypePriceService {
    List<TypePrices> getAllTypePrices();
    Optional<TypePrices> getTypePriceById(Long id);
    TypePrices createTypePrice(TypePricesDTO typePricesdto);
    TypePrices saveTypePrice(TypePrices typePrices);
    TypePrices updateTypePrice(Long id, TypePrices typePricesDetails) throws DataNotFoundException;
    void deleteTypePrice(Long id) throws DataNotFoundException;
}
