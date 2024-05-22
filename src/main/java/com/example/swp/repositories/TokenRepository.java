package com.example.swp.repositories;


import com.example.swp.entities.Users;
import com.example.swp.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(Users user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
    List<Token> findByUserId(Long userId);
}

