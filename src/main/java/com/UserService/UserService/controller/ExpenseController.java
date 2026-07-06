package com.UserService.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserService.UserService.dto.ExpenseFilterDTO;
import com.UserService.UserService.dto.Expensedto;
import com.UserService.UserService.entity.ExpenseEntity;
import com.UserService.UserService.entity.UserEntity;
import com.UserService.UserService.repo.UserRepo;
import com.UserService.UserService.service.ExpenseService;
import com.UserService.UserService.service.JwtService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private final JwtService jwtService;
	
	public ExpenseController(
	        ExpenseService expenseService,
	        JwtService jwtService
	){

	    this.expenseService = expenseService;
	    this.jwtService = jwtService;

	}
	
	@PostMapping
	public ExpenseEntity addExpense(

	        @RequestBody Expensedto request,

	        @RequestHeader("Authorization") String authHeader

	){


	    if(authHeader == null ||
	       !authHeader.startsWith("Bearer ")){

	        throw new RuntimeException(
	                "Invalid Token"
	        );

	    }



	    String token =
	            authHeader.substring(7);



	    String email =
	            jwtService.extractEmail(token);



	    return expenseService.addExpense(
	            request,
	            email
	    );

	}
	
	@PostMapping("/filter")
	public List<ExpenseEntity> filterExpenses(
	        @RequestBody ExpenseFilterDTO filter) {

	    return expenseService.filterExpenses(filter);

	}
	
	@GetMapping
	public List<ExpenseEntity> getExpenses(
	        @RequestHeader("Authorization") String authHeader
	){


	    String token =
	            authHeader.substring(7);



	    String email =
	            jwtService.extractEmail(token);



	    UserEntity user =
	    		userRepo
	            .findByEmail(email)
	            .orElseThrow(
	                () -> new RuntimeException("User not found")
	            );



	    return expenseService.getExpenses(
	            user.getId()
	    );

	}
	 
	 
	 @PutMapping("/{id}")
	    public ExpenseEntity updateExpense(

	            @PathVariable Long id,

	            @RequestBody ExpenseEntity expense

	    ){

	        return expenseService.updateExpense(
	                id,
	                expense
	        );
	 }

	 @DeleteMapping("/{id}")
	 public String deleteExpense(

	         @PathVariable Long id,

	         @RequestHeader("Authorization") String authHeader

	 ){


	     String token =
	             authHeader.substring(7);



	     String email =
	             jwtService.extractEmail(token);



	     UserEntity user =
	             userRepo
	             .findByEmail(email)
	             .orElseThrow(
	                     () -> new RuntimeException("User not found")
	             );



	     expenseService.deleteExpense(
	             id,
	             user.getId()
	     );



	     return "Expense deleted successfully";

	 }

}
