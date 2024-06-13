package com.example.swp.repositories;

import com.example.swp.entities.Counters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterRepository extends JpaRepository<Counters, Long> {
    List<Counters> findByCounterName(String name);
}
