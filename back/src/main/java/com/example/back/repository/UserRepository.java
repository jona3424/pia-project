package com.example.back.repository;

import com.example.back.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByUsernameAndPasswordAndIsActive(String username, String password, boolean isActive);
    List<Users> findByIsActive(boolean isActive);
    String findScurityQuestionByUsername(String username);

    String findScurityAnswerByUsername(String username);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUsernameAndPassword(String username, String password);

    List<Users>  findByRoleAndIsActive(String role, boolean isActive);
}

