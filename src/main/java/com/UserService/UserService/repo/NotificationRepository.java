package com.UserService.UserService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserService.UserService.entity.NotificationEntity;
import com.UserService.UserService.entity.UserEntity;

public interface NotificationRepository
        extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity>
    findByUserOrderByCreatedAtDesc(UserEntity user);

    List<NotificationEntity>
    findByUserAndIsReadFalseOrderByCreatedAtDesc(UserEntity user);

}
