package com.UserService.UserService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.UserService.UserService.entity.ExpenseEntity;

public interface ExpenseRepo extends JpaRepository<ExpenseEntity, Long>,
        JpaSpecificationExecutor<ExpenseEntity> {

    // Get all expenses of a user
    List<ExpenseEntity> findByUser_Id(Long userId);
    

    // Total expense of a user
    @Query("""
            SELECT COALESCE(SUM(e.amount),0)
            FROM ExpenseEntity e
            WHERE e.user.id = :userId
            """)
    Double getTotalExpense(@Param("userId") Long userId);

}