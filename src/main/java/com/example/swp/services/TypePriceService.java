package com.example.swp.services;


import com.example.swp.dtos.TypePricesDTO;
import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.TypePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypePriceService implements ITypePriceService{

    private final TypePriceRepository typePriceRepository;

    @Override
    public List<TypePrices> getAllTypePrices() {
        return typePriceRepository.findAll();
    }

    @Override
    public Optional<TypePrices> getTypePriceById(Long id) {
        return typePriceRepository.findById(id);
    }

    @Override
    public TypePrices createTypePrice(TypePricesDTO typePricesdto) {
            TypePrices typePrices = new TypePrices();
            typePrices.setDate(typePricesdto.getDate());
            typePrices.setBuyPricePerGram(typePricesdto.getBuyPricePerGram());
            typePrices.setSellPricePerGram(typePricesdto.getSellPricePerGram());
            typePrices.setType(typePricesdto.getType());
            return typePriceRepository.save(typePrices);
        }


    @Override
    public TypePrices saveTypePrice(TypePrices typePrices) {
        return typePriceRepository.save(typePrices);
    }

    @Override
    public TypePrices updateTypePrice(Long id, TypePrices typePricesDetails) throws DataNotFoundException {
        TypePrices typePrices = typePriceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("TypePrices not found for this id :: " + id));

        typePrices.setDate(typePricesDetails.getDate());
        typePrices.setBuyPricePerGram(typePricesDetails.getBuyPricePerGram());
        typePrices.setSellPricePerGram(typePricesDetails.getSellPricePerGram());
        typePrices.setType(typePricesDetails.getType());

        return typePriceRepository.save(typePrices);
    }

    @Override
    public void deleteTypePrice(Long id) throws DataNotFoundException {
        TypePrices typePrices = typePriceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("TypePrices not found for this id :: " + id));
        typePriceRepository.delete(typePrices);
    }
}
