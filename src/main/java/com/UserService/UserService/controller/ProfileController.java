package com.UserService.UserService.controller;

import com.UserService.UserService.dto.ProfileDTO;
import com.UserService.UserService.dto.UpdateProfileDTO;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ProfileDTO getProfile(Authentication authentication) {

        UserEntity user = userRepo
                .findByEmail(authentication.getName())
                .orElseThrow();

        return new ProfileDTO(
                user.getName(),
                user.getEmail()
        );
    }

    @PutMapping
    public String updateProfile(
            @RequestBody UpdateProfileDTO dto,
            Authentication authentication) {

        UserEntity user = userRepo
                .findByEmail(authentication.getName())
                .orElseThrow();

        user.setName(dto.getName());

        if(dto.getPassword()!=null && !dto.getPassword().isBlank()){

            user.setPassword(
                    passwordEncoder.encode(dto.getPassword())
            );

        }

        userRepo.save(user);

        return "Profile Updated Successfully";
    }

}