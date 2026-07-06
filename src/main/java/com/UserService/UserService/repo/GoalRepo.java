package com.UserService.UserService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserService.UserService.entity.GoalEntity;

public interface GoalRepo extends JpaRepository<GoalEntity, Long> {

    List<GoalEntity> findByUserId(Long userId);

}
