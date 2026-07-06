package com.UserService.UserService.dto;

import java.time.LocalDate;

public class Expensedto {
	
	 public Double amount;

	 public String category;

	 public String description;

	 public LocalDate expenseDate;

	 public Double getAmount() {
		 return amount;
	 }

	 public String getCategory() {
		 return category;
	 }

	 public void setCategory(String category) {
		 this.category = category;
	 }

	 public String getDescription() {
		 return description;
	 }

	 public void setDescription1(String description) {
		 this.description = description;
	 }

	 public LocalDate getExpenseDate() {
		 return expenseDate;
	 }

	 public void setExpenseDate1(LocalDate expenseDate) {
		 this.expenseDate = expenseDate;
	 }
	 
	  public void setAmount(Double amount){

		    this.amount = amount;

		}


		public void setDescription(String description){

		    this.description = description;

		}


		public void setExpenseDate(LocalDate expenseDate){

		    this.expenseDate = expenseDate;

		}


}
