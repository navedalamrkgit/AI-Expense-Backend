package com.UserService.UserService.service;


import com.UserService.UserService.entity.ExpenseEntity;
import com.UserService.UserService.repo.ExpenseRepo;

import org.springframework.stereotype.Service;


	import java.util.List;



	@Service
	public class SpendingAdvisorService {



	    private final ExpenseRepo expenseRepo;



	    public SpendingAdvisorService(
	            ExpenseRepo expenseRepo
	    ){

	        this.expenseRepo = expenseRepo;

	    }





	    public String generateAdvice(Long userId){



	        List<ExpenseEntity> expenses =
	                expenseRepo
	                .findByUser_Id(userId);



	        double total = 0;


	        double food = 0;

	        double shopping = 0;

	        double travel = 0;



	        for(ExpenseEntity expense : expenses){


	            total += expense.getAmount();



	            if(expense.getCategory()
	                    .equalsIgnoreCase("Food")){


	                food += expense.getAmount();

	            }



	            if(expense.getCategory()
	                    .equalsIgnoreCase("Shopping")){


	                shopping += expense.getAmount();

	            }



	            if(expense.getCategory()
	                    .equalsIgnoreCase("Travel")){


	                travel += expense.getAmount();

	            }

	        }





	        if(shopping > total * 0.4){


	            return
	            "Your shopping expenses are high. Try reducing unnecessary purchases.";

	        }



	        if(food > total * 0.3){


	            return
	            "Your food expenses are increasing. Consider planning meals.";

	        }



	        if(travel > total * 0.3){


	            return
	            "Your travel expenses are high this month.";

	        }




	        return
	        "Your spending looks balanced. Keep tracking your expenses.";

	    }

	}

