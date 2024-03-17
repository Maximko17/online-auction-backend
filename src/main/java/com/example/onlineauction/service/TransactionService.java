package com.example.onlineauction.service;

import com.example.onlineauction.entity.TransactionEntity;
import com.example.onlineauction.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionEntity save(TransactionEntity entity) {
        return repository.save(entity);
    }
}
