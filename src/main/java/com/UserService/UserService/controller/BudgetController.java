package com.UserService.UserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.UserService.UserService.dto.BudgetDto;
import com.UserService.UserService.entity.BudgetEntity;
import com.UserService.UserService.service.BudgetService;
import com.UserService.UserService.service.JwtService;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:5173")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private JwtService jwtService;

    // ================= SAVE BUDGET =================

    @PostMapping
    public BudgetEntity saveBudget(

            @RequestBody BudgetDto request,

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.saveBudget(request, email);

    }

    // ================= GET BUDGET =================

    @GetMapping
    public BudgetEntity getBudget(

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.getBudget(email);

    }

    // ================= UPDATE BUDGET =================

    @PutMapping
    public BudgetEntity updateBudget(

            @RequestBody BudgetDto request,

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.updateBudget(request, email);

    }

    // ================= TOTAL SPENT =================

    @GetMapping("/spent")
    public Double getSpent(

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.getSpentAmount(email);

    }

    // ================= REMAINING =================

    @GetMapping("/remaining")
    public Double getRemaining(

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.getRemainingBudget(email);

    }

    // ================= BUDGET USAGE =================

    @GetMapping("/usage")
    public Double getUsage(

            @RequestHeader("Authorization") String authHeader

    ) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return budgetService.getBudgetUsage(email);

    }

}