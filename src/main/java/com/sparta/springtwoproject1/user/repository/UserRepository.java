package com.sparta.springtwoproject1.user.repository;

import com.sparta.springtwoproject1.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String name);
}
