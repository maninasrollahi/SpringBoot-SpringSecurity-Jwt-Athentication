package com.example.springbootsecurityjwt.security.baseInfo.repository;

import com.example.springbootsecurityjwt.security.baseInfo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}
