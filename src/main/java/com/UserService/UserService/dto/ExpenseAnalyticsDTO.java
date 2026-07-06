package com.UserService.UserService.dto;

import java.util.Map;

public class ExpenseAnalyticsDTO {

    private Double totalExpense;

    private Long totalTransactions;

    private Map<String, Double> categoryData;

    public ExpenseAnalyticsDTO() {
    }

    public ExpenseAnalyticsDTO(
            Double totalExpense,
            Long totalTransactions,
            Map<String, Double> categoryData
    ) {
        this.totalExpense = totalExpense;
        this.totalTransactions = totalTransactions;
        this.categoryData = categoryData;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Map<String, Double> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(Map<String, Double> categoryData) {
        this.categoryData = categoryData;
    }
}