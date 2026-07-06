package com.UserService.UserService.dto;

public class UpdateProfileDTO {

    private String name;
    private String password;

    public UpdateProfileDTO() {
    }

    public UpdateProfileDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}