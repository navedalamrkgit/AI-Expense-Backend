package com.UserService.UserService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import com.UserService.UserService.dto.ExpenseAnalyticsDTO;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.UserRepo;
import com.UserService.UserService.service.AnalyticsService;



@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {


@Autowired 
    private final AnalyticsService analyticsService;
@Autowired
    private final UserRepo userRepo;



    public AnalyticsController(
            AnalyticsService analyticsService,
            UserRepo userRepo
    ){

        this.analyticsService = analyticsService;
        this.userRepo = userRepo;

    }





    @GetMapping
    public ExpenseAnalyticsDTO getAnalytics(
            Authentication authentication
    ){



        String email =
                authentication.getName();



        UserEntity user =
                userRepo
                .findByEmail(email)
                .orElseThrow();



        return analyticsService
                .getAnalytics(
                        user.getId()
                );


    }

}
