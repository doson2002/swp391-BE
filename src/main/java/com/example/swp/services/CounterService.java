package com.example.swp.services;

import com.example.swp.dtos.CounterDTO;
import com.example.swp.entities.Counters;
import com.example.swp.exceptions.DataNotFoundException;
import com.example.swp.repositories.CounterRepository;
import com.example.swp.repositories.OrderDetailRepository;
import com.example.swp.repositories.ProductRepository;
import com.example.swp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CounterService implements ICounterService{

    private final CounterRepository counterRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public Counters createCounter(CounterDTO counterDTO) {
        Counters newCounter = Counters
                .builder()
                .location(counterDTO.getLocation())
                .counterName(counterDTO.getCounterName())
                .build();
        return counterRepository.save(newCounter);
    }

    @Override
    public List<Counters> getCountersByName(String name) throws DataNotFoundException {
        List<Counters> existingCounter = counterRepository.findByCounterName(name);
        if (existingCounter==null){
            throw new DataNotFoundException("No Counter was found with name:" +name);
        }else
            return existingCounter;
    }

    @Override
    public List<Counters> getAllCounters() {
        return counterRepository.findAll();
    }

    @Override
    public Counters updateCounter(Long id, CounterDTO counterDTO) throws DataNotFoundException {
        Counters existingCounter = counterRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Counter not found with id:" + id));
        existingCounter.setCounterName(counterDTO.getCounterName());
        existingCounter.setLocation(counterDTO.getLocation());

        return counterRepository.save(existingCounter);
    }

    @Override
    public void deleteCounter(Long counterId) throws DataNotFoundException {
        Counters existingCounter = counterRepository.findById(counterId)
                .orElseThrow(()->new DateTimeException("Counter not found with id: " +counterId));
        orderDetailRepository.deleteByCounterId(counterId);
        productRepository.deleteByCounterId(counterId);
        userRepository.deleteByCounterId(counterId);

        counterRepository.delete(existingCounter);
    }
}
