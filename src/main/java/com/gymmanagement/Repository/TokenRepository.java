package com.gymmanagement.Repository;


import com.gymmanagement.Modal.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
select t from Token t inner join Users u on t.users.id = u.id where t.users.id = :userId and t.loggedOut = false""")
    List<Token> findAllTokensByUsers(Long userId);

    Optional<Token> findByToken(String token);
}
