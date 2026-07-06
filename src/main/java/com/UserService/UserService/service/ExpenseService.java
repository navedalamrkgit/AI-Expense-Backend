package com.UserService.UserService.service;

import com.UserService.UserService.dto.ExpenseFilterDTO;
import com.UserService.UserService.dto.Expensedto;
import com.UserService.UserService.entity.ExpenseEntity;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.ExpenseRepo;
import com.UserService.UserService.repo.UserRepo;
import com.UserService.UserService.specification.ExpenseSpecification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AIService aiService;

    @Autowired
    private ReceiptAIService receiptAIService;

    @Autowired
    private NotificationService notificationService;

    public ExpenseService() {
    }

    // ===================== ADD EXPENSE =====================

    public ExpenseEntity addExpense(
            Expensedto request,
            String email
    ) {

        UserEntity user = userRepo
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExpenseEntity expense = new ExpenseEntity();

        expense.setAmount(request.getAmount());

        if (request.getCategory() == null || request.getCategory().isBlank()) {

            String category = aiService.predictCategory(
                    request.getDescription()
            );

            expense.setCategory(category);

        } else {

            expense.setCategory(request.getCategory());

        }

        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setUser(user);

        // Save expense only once
        ExpenseEntity savedExpense = expenseRepo.save(expense);

        // Check budget notifications
        notificationService.checkBudgetNotifications(email);

        return savedExpense;
    }

    // ===================== GET =====================

    public List<ExpenseEntity> getExpenses(Long userId) {

        return expenseRepo.findByUser_Id(userId);

    }

    // ===================== UPDATE =====================

    public ExpenseEntity updateExpense(
            Long id,
            ExpenseEntity updatedExpense
    ) {

        ExpenseEntity expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        expense.setExpenseDate(updatedExpense.getExpenseDate());

        return expenseRepo.save(expense);

    }

    // ===================== FILTER =====================

    public List<ExpenseEntity> filterExpenses(
            ExpenseFilterDTO filter
    ) {

        Specification<ExpenseEntity> specification =
                ExpenseSpecification.filterExpenses(filter);

        return expenseRepo.findAll(specification);

    }

    // ===================== DELETE =====================

    public void deleteExpense(
            Long expenseId,
            Long userId
    ) {

        ExpenseEntity expense = expenseRepo.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(userId)) {

            throw new RuntimeException("Unauthorized delete");

        }

        expenseRepo.delete(expense);

    }

    // ===================== RECEIPT =====================

    public ExpenseEntity addExpenseFromReceipt(
            Expensedto request,
            String token
    ) {

        String email = jwtService.extractEmail(token);

        UserEntity user = userRepo
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExpenseEntity expense = new ExpenseEntity();

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        String category = receiptAIService.predictCategory(
                request.getDescription()
        );

        expense.setCategory(category);
        expense.setUser(user);

        ExpenseEntity savedExpense = expenseRepo.save(expense);

        // Check notifications for receipt expenses also
        notificationService.checkBudgetNotifications(email);

        return savedExpense;
    }

}