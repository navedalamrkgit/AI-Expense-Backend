package com.UserService.UserService.repo;

import com.UserService.UserService.entity.ExpenseEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseRepository extends
        JpaRepository<ExpenseEntity, Long>,
        JpaSpecificationExecutor<ExpenseEntity> {
	
	List<ExpenseEntity> findByUser_Id(Long userId);

}
