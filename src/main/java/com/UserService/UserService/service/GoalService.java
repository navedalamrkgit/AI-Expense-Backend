package com.UserService.UserService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserService.UserService.dto.GoalDto;
import com.UserService.UserService.entity.GoalEntity;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.GoalRepo;
import com.UserService.UserService.repo.UserRepo;

@Service
public class GoalService {

    @Autowired
    private GoalRepo goalRepo;

    @Autowired
    private UserRepo userRepo;

    public GoalEntity addGoal(GoalDto dto, String email) {

        UserEntity user = userRepo.findByEmail(email).orElseThrow();

        GoalEntity goal = new GoalEntity();

        goal.setTitle(dto.getTitle());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setSavedAmount(dto.getSavedAmount());
        goal.setTargetDate(dto.getTargetDate());

        if (dto.getSavedAmount() >= dto.getTargetAmount()) {
            goal.setStatus("Completed");
        } else {
            goal.setStatus("In Progress");
        }

        goal.setUser(user);

        return goalRepo.save(goal);
    }

    public List<GoalEntity> getGoals(String email) {

        UserEntity user = userRepo.findByEmail(email).orElseThrow();

        return goalRepo.findByUserId(user.getId());
    }

    public GoalEntity updateGoal(Long id, GoalDto dto) {

        GoalEntity goal = goalRepo.findById(id).orElseThrow();

        goal.setTitle(dto.getTitle());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setSavedAmount(dto.getSavedAmount());
        goal.setTargetDate(dto.getTargetDate());

        if (dto.getSavedAmount() >= dto.getTargetAmount()) {
            goal.setStatus("Completed");
        } else {
            goal.setStatus("In Progress");
        }

        return goalRepo.save(goal);
    }

    public void deleteGoal(Long id) {

        goalRepo.deleteById(id);

    }

}