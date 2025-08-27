package com.Security.repository;


import com.Security.entity.User;
import com.Security.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  List<Token> findAllValidTokenByUser(Long id);

  Optional<Token> findByToken(String token);
}
