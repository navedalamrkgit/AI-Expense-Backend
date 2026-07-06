package com.UserService.UserService.dto;

public class ResponseDto {
	
	private String token;
 public ResponseDto(String token) {
	 
	 this.token =token;
 }
 
 public String getToken() {
	 
	 return token;
 }

}
