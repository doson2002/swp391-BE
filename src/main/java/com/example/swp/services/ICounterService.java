package com.example.swp.services;

import com.example.swp.dtos.CounterDTO;
import com.example.swp.dtos.CustomersDTO;
import com.example.swp.entities.Counters;
import com.example.swp.entities.Customers;
import com.example.swp.exceptions.DataNotFoundException;

import java.util.List;

public interface ICounterService {
    Counters createCounter(CounterDTO counterDTO);

    List<Counters> getCountersByName(String name) throws DataNotFoundException;

    List<Counters> getAllCounters();
    Counters updateCounter(Long id, CounterDTO counterDTO) throws DataNotFoundException;

    void deleteCounter(Long id) throws DataNotFoundException;
}
