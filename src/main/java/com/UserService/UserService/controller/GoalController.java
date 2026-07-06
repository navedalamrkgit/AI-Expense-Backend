package com.UserService.UserService.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.UserService.UserService.dto.GoalDto;
import com.UserService.UserService.entity.GoalEntity;
import com.UserService.UserService.service.GoalService;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin("*")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public GoalEntity addGoal(
            @RequestBody GoalDto dto,
            Principal principal
    ) {

        return goalService.addGoal(dto, principal.getName());

    }

    @GetMapping
    public List<GoalEntity> getGoals(
            Principal principal
    ) {

        return goalService.getGoals(principal.getName());

    }

    @PutMapping("/{id}")
    public GoalEntity updateGoal(
            @PathVariable Long id,
            @RequestBody GoalDto dto
    ) {

        return goalService.updateGoal(id, dto);

    }

    @DeleteMapping("/{id}")
    public String deleteGoal(
            @PathVariable Long id
    ) {

        goalService.deleteGoal(id);

        return "Goal Deleted Successfully";

    }

}
