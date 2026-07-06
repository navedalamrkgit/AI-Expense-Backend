package com.UserService.UserService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserService.UserService.dto.ExpenseAnalyticsDTO;
import com.UserService.UserService.entity.BudgetEntity;
import com.UserService.UserService.entity.NotificationEntity;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.BudgetRepo;
import com.UserService.UserService.repo.NotificationRepository;
import com.UserService.UserService.repo.UserRepo;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BudgetRepo budgetRepo;

    @Autowired
    private AnalyticsService analyticsService;

    // =========================
    // CREATE NOTIFICATION
    // =========================

    public NotificationEntity createNotification(
            String email,
            String message,
            String type
    ) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        NotificationEntity notification = new NotificationEntity();

        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);

        // Uncomment if your entity has this field
        // notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    // =========================
    // CHECK BUDGET NOTIFICATIONS
    // =========================

    public void checkBudgetNotifications(String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        BudgetEntity budget = budgetRepo.findByUserId(user.getId())
                .orElse(null);

        if (budget == null) {
            return;
        }

        ExpenseAnalyticsDTO analytics =
                analyticsService.getAnalytics(user.getId());

        double spent = analytics.getTotalExpense();

        double monthlyBudget = budget.getMonthlyBudget();

        if (monthlyBudget <= 0) {
            return;
        }

        double usage = (spent / monthlyBudget) * 100;

        if (usage >= 100) {

            createNotification(
                    email,
                    "🚨 Budget exceeded! You have spent ₹" + spent,
                    "DANGER"
            );

        } else if (usage >= 80) {

            createNotification(
                    email,
                    "⚠️ Budget usage reached " +
                            String.format("%.1f", usage) + "%",
                    "WARNING"
            );
        }
    }

    // =========================
    // GET ALL
    // =========================

    public List<NotificationEntity> getNotifications(
            String email
    ) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user);
    }

    // =========================
    // GET UNREAD
    // =========================

    public List<NotificationEntity> getUnreadNotifications(
            String email
    ) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return notificationRepository
                .findByUserAndIsReadFalseOrderByCreatedAtDesc(user);
    }

    // =========================
    // MARK AS READ
    // =========================

    public NotificationEntity markAsRead(Long id) {

        NotificationEntity notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found"));

        notification.setIsRead(true);

        return notificationRepository.save(notification);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteNotification(Long id) {

        notificationRepository.deleteById(id);

    }

}