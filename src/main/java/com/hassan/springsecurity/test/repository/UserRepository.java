package com.hassan.springsecurity.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hassan.springsecurity.test.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);
}
