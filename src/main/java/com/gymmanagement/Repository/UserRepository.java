package com.gymmanagement.Repository;


import com.gymmanagement.Modal.Role;
import com.gymmanagement.Modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);

    List<Users> findAllByMobileno(String mobileno);
    List<Users> findAllByEmailid(String emailid);
    List<Users> findAllByUsername(String username);

    Optional<Users> findAllById(Long id);

    List<Users> findAllByRole(Role role);
}

