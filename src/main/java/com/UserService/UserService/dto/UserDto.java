package com.UserService.UserService.dto;

import lombok.Data;

@Data 
public class UserDto {
	
	private String name;
	
	private String email;
	
	private String password;
	

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

	

}
