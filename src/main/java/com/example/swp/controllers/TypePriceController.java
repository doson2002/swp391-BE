package com.example.swp.controllers;

import com.example.swp.entities.TypePrices;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.services.TypePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/typeprice")
@RequiredArgsConstructor
public class TypePriceController {

    private final TypePriceService typePriceService;

    @GetMapping("/get_all_type_prices")
    public List<TypePrices> getAllTypePrices() {
        return typePriceService.getAllTypePrices();
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<TypePrices> getTypePriceById(@PathVariable(value = "id") Long id) throws DataNotFoundException {
        TypePrices typePrices = typePriceService.getTypePriceById(id)
                .orElseThrow(() -> new DataNotFoundException("TypePrices not found for this id :: " + id));
        return ResponseEntity.ok().body(typePrices);
    }

    @PostMapping("/create_type_prices")
    public TypePrices createTypePrice(@RequestBody TypePrices typePrices) {
        return typePriceService.saveTypePrice(typePrices);
    }

    @PutMapping("/update_type_prices/{id}")
    public ResponseEntity<TypePrices> updateTypePrice(@PathVariable(value = "id") Long id,
                                                      @RequestBody TypePrices typePricesDetails) throws DataNotFoundException {
        TypePrices updatedTypePrices = typePriceService.updateTypePrice(id, typePricesDetails);
        return ResponseEntity.ok(updatedTypePrices);
    }

    @DeleteMapping("/delete_type_prices/{id}")
    public Map<String, Boolean> deleteTypePrice(@PathVariable(value = "id") Long id) throws DataNotFoundException {
        typePriceService.deleteTypePrice(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
