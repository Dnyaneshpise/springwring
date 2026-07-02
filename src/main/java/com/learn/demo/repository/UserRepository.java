package com.learn.demo.repository;

import com.learn.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByName(String name);

    List<UserEntity> findByRole(String role);
}