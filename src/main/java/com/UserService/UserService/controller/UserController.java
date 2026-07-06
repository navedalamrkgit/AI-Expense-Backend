package com.UserService.UserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserService.UserService.dto.LoginDto;
import com.UserService.UserService.dto.ResponseDto;
import com.UserService.UserService.dto.UserDto;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired 
	UserService userservice; 
	  
	@PostMapping("register")
	public UserEntity register(@RequestBody UserDto request) {
		//TODO: process POST request
		
		return userservice.register(request);
	}
	
	@PostMapping("/login")
	public ResponseDto login(
	@RequestBody LoginDto request
	){

	String token= userservice.login(request);

	return new ResponseDto(token);

	}
	
}
