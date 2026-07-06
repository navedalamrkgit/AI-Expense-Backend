package com.UserService.UserService.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "expenses")
public class ExpenseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    public Double amount;


    public String description;


    public String category;


    public LocalDate expenseDate;


    @ManyToOne
    @JoinColumn(name="user_id")
    public UserEntity user;



    public ExpenseEntity(){}


    public Long getId(){
        return id;
    }


    public Double getAmount(){
        return amount;
    }


    public String getCategory(){
        return category;
    }


    public String getDescription(){
        return description;
    }


    public LocalDate getExpenseDate(){
        return expenseDate;
    }


    public UserEntity getUser(){
        return user;
    }



    public void setAmount(Double amount){
        this.amount = amount;
    }


    public void setCategory(String category){
        this.category = category;
    }


    public void setDescription(String description){
        this.description = description;
    }


    public void setExpenseDate(LocalDate expenseDate){
        this.expenseDate = expenseDate;
    }


    public void setUser(UserEntity user){
        this.user = user;
    }

}