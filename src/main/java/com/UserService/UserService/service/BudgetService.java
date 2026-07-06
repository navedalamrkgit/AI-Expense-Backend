package com.UserService.UserService.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserService.UserService.dto.BudgetDto;
import com.UserService.UserService.entity.BudgetEntity;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.BudgetRepo;
import com.UserService.UserService.repo.ExpenseRepo;
import com.UserService.UserService.repo.UserRepo;

@Service
public class BudgetService {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AnalyticsService analyticsService; 
    @Autowired
    private BudgetRepo budgetRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ExpenseRepo expenseRepo;

    // ================= SAVE BUDGET =================

    public BudgetEntity saveBudget(BudgetDto request, String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BudgetEntity budget = budgetRepo.findByUserId(user.getId())
                .orElse(new BudgetEntity());

        LocalDate today = LocalDate.now();

        budget.setMonthlyBudget(request.getMonthlyBudget());
        budget.setMonth(today.getMonthValue());
        budget.setYear(today.getYear());
        budget.setUser(user);

        return budgetRepo.save(budget);
    }

    // ================= GET BUDGET =================

    public BudgetEntity getBudget(String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return budgetRepo.findByUserId(user.getId())
                .orElse(null);
    }

    // ================= UPDATE BUDGET =================

    public BudgetEntity updateBudget(BudgetDto request, String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BudgetEntity budget = budgetRepo.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        LocalDate today = LocalDate.now();

        budget.setMonthlyBudget(request.getMonthlyBudget());
        budget.setMonth(today.getMonthValue());
        budget.setYear(today.getYear());

        return budgetRepo.save(budget);
    }

    // ================= TOTAL SPENT =================

    public Double getSpentAmount(String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Double total = expenseRepo.getTotalExpense(user.getId());

        return total == null ? 0.0 : total;
    }

    // ================= REMAINING BUDGET =================

    public Double getRemainingBudget(String email) {

        BudgetEntity budget = getBudget(email);

        if (budget == null) {
            return 0.0;
        }

        Double spent = getSpentAmount(email);

        return budget.getMonthlyBudget() - spent;
    }

    // ================= BUDGET USAGE =================

    public Double getBudgetUsage(String email) {

        BudgetEntity budget = getBudget(email);

        if (budget == null) {
            return 0.0;
        }

        if (budget.getMonthlyBudget() == 0) {
            return 0.0;
        }

        Double spent = getSpentAmount(email);

        return (spent / budget.getMonthlyBudget()) * 100;
    }

    // ================= BUDGET EXCEEDED =================

    public boolean isBudgetExceeded(String email) {

        return getSpentAmount(email) > getBudget(email).getMonthlyBudget();

    }

    // ================= 80% BUDGET USED =================

    public boolean isBudgetAlmostReached(String email) {

        return getBudgetUsage(email) >= 80;

    }
    
    private void checkBudgetNotification(String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BudgetEntity budget = budgetRepo.findByUserId(user.getId())
                .orElse(null);

        if (budget == null) {
            return;
        }

        double spent = analyticsService.getAnalytics(user.getId()).getTotalExpense();

        double monthlyBudget = budget.getMonthlyBudget();

        if (monthlyBudget <= 0) {
            return;
        }

        double usage = (spent / monthlyBudget) * 100;

        if (usage >= 100) {

            notificationService.createNotification(
                    email,
                    "🚨 Budget exceeded! You have spent ₹" + spent,
                    "DANGER"
            );

        } else if (usage >= 80) {

            notificationService.createNotification(
                    email,
                    "⚠️ You have used " + String.format("%.1f", usage) + "% of your budget.",
                    "WARNING"
            );
        }
    }

}

