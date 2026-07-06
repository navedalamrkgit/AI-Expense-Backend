package com.UserService.UserService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UserService.UserService.entity.UserEntity;

@Repository 
public interface UserRepo extends JpaRepository<UserEntity ,Long> {
	
	  boolean existsByEmail(String email);
	  Optional<UserEntity> findByEmail(String email);


}
