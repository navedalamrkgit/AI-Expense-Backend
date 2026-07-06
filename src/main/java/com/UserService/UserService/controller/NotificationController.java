package com.UserService.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.UserService.UserService.entity.NotificationEntity;
import com.UserService.UserService.service.JwtService;
import com.UserService.UserService.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtService jwtService;

    // ===========================
    // GET ALL NOTIFICATIONS
    // ===========================

    @GetMapping
    public List<NotificationEntity> getNotifications(

            @RequestHeader("Authorization") String token

    ) {

        String email = jwtService.extractEmail(
                token.substring(7)
        );

        return notificationService.getNotifications(email);

    }

    // ===========================
    // GET UNREAD
    // ===========================

    @GetMapping("/unread")
    public List<NotificationEntity> getUnreadNotifications(

            @RequestHeader("Authorization") String token

    ) {

        String email = jwtService.extractEmail(
                token.substring(7)
        );

        return notificationService.getUnreadNotifications(email);

    }

    // ===========================
    // CREATE NOTIFICATION
    // ===========================

    @PostMapping
    public NotificationEntity createNotification(

            @RequestHeader("Authorization") String token,

            @RequestParam String message,

            @RequestParam String type

    ) {

        String email = jwtService.extractEmail(
                token.substring(7)
        );

        return notificationService.createNotification(
                email,
                message,
                type
        );

    }

    // ===========================
    // MARK AS READ
    // ===========================

    @PutMapping("/{id}/read")
    public NotificationEntity markAsRead(

            @PathVariable Long id

    ) {

        return notificationService.markAsRead(id);

    }

    // ===========================
    // DELETE
    // ===========================

    @DeleteMapping("/{id}")
    public String deleteNotification(

            @PathVariable Long id

    ) {

        notificationService.deleteNotification(id);

        return "Notification Deleted Successfully";

    }

}