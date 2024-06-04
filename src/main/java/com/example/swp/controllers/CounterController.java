package com.example.swp.controllers;

import com.example.swp.dtos.CounterDTO;
import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Counters;
import com.example.swp.entities.Customers;
import com.example.swp.entities.Products;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.services.ICounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.Counter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/counters")
@RequiredArgsConstructor
public class CounterController {

    private final ICounterService counterService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createCounter(@Valid @RequestBody CounterDTO counterDTO,
                                           BindingResult result) {
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Counters newCounter = counterService.createCounter(counterDTO);

            return ResponseEntity.ok(newCounter);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody CounterDTO counterDTO)
            throws DataNotFoundException {
        try{
            Counters updatedCounter = counterService.updateCounter(id, counterDTO);
            return ResponseEntity.ok(updatedCounter);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{counterId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCounter(@Valid @PathVariable Long counterId) {
        try {
            counterService.deleteCounter(counterId);
            return ResponseEntity.ok("Counter deleted successfully.");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get_counter_by_name")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public  ResponseEntity<?> getCounterByName(@RequestParam String name) throws DataNotFoundException {
        try{
            List<Counters> counterList = counterService.getCountersByName(name);
            return ResponseEntity.ok(counterList);
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/get_all_counters")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCounters() {
        try{
            List<Counters> countersList = counterService.getAllCounters();
            return ResponseEntity.ok(countersList);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }



}
