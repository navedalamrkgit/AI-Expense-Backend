package com.UserService.UserService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UserService.UserService.dto.LoginDto;
import com.UserService.UserService.dto.UserDto;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.UserRepo;

@Service 
public class UserService {
	
	@Autowired 
	UserRepo userRepo;
	
	@Autowired 
	 JwtService jwtService;

	public UserEntity register(UserDto request) {
		  if(userRepo.existsByEmail(request.getEmail())){

	            throw new RuntimeException("Email already exists");
	}
		  UserEntity user = new UserEntity();

		  user.setName(request.getName());
		  user.setEmail(request.getEmail());
		  user.setPassword(request.getPassword());
	
	return userRepo.save(user);
	}
	
	public String login(LoginDto request){


	    UserEntity user = userRepo
	            .findByEmail(request.getEmail())
	            .orElseThrow(
	                    ()->new RuntimeException("User not found")
	            );


	    if(!user.getPassword()
	            .equals(request.getPassword())){


	        throw new RuntimeException("Invalid password");

	    }


	    return jwtService.generateToken(user.getEmail());

	}
 
}
