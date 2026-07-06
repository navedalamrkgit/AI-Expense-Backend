package com.UserService.UserService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserService.UserService.entity.BudgetEntity;

public interface BudgetRepo extends JpaRepository<BudgetEntity, Long> {

    Optional<BudgetEntity> findByUserId(Long userId);

}
