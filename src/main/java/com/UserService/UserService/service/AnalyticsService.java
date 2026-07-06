package com.UserService.UserService.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.UserService.UserService.dto.ExpenseAnalyticsDTO;
import com.UserService.UserService.entity.ExpenseEntity;
import com.UserService.UserService.repo.ExpenseRepo;

@Service
public class AnalyticsService {

    private final ExpenseRepo expenseRepo;

    public AnalyticsService(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    public ExpenseAnalyticsDTO getAnalytics(Long userId) {

        // Get all expenses
        List<ExpenseEntity> expenses = expenseRepo.findByUser_Id(userId);

        // Get total directly from database
        Double totalExpense = expenseRepo.getTotalExpense(userId);

        Map<String, Double> categoryMap = new HashMap<>();

        for (ExpenseEntity expense : expenses) {

            String category = expense.getCategory();

            if (category == null || category.isBlank()) {
                category = "Others";
            }

            categoryMap.put(
                    category,
                    categoryMap.getOrDefault(category, 0.0)
                            + expense.getAmount());
        }

        return new ExpenseAnalyticsDTO(
                totalExpense,
                (long) expenses.size(),
                categoryMap);
    }
}
