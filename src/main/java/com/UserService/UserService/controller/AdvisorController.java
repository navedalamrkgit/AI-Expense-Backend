package com.UserService.UserService.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.UserRepo;
import com.UserService.UserService.service.SpendingAdvisorService;



@RestController
@RequestMapping("/api/advisor")
public class AdvisorController {



    private final SpendingAdvisorService advisorService;

    private final UserRepo userRepo;



    public AdvisorController(
            SpendingAdvisorService advisorService,
            UserRepo userRepository
    ){

        this.advisorService = advisorService;
        this.userRepo = userRepository;

    }




    @GetMapping
    public String getAdvice(
            Authentication authentication
    ){


        String email =
                authentication.getName();



        UserEntity user =
                userRepo
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );



        return advisorService
                .generateAdvice(
                        user.getId()
                );

    }

}