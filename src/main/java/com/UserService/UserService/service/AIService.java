package com.UserService.UserService.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {
	
    public String predictCategory(String description){


        description =
                description.toLowerCase();



        if(description.contains("food")
                || description.contains("restaurant")
                || description.contains("pizza")
                || description.contains("grocery")){

            return "Food";

        }


        if(description.contains("uber")
                || description.contains("bus")
                || description.contains("train")){

            return "Travel";

        }


        if(description.contains("shirt")
                || description.contains("shopping")){

            return "Shopping";

        }


        return "Other";

    }

}