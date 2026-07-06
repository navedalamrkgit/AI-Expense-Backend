package com.UserService.UserService.controller;


import java.io.File;
import java.time.LocalDate;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.UserService.UserService.dto.Expensedto;
import com.UserService.UserService.entity.ExpenseEntity;
import com.UserService.UserService.service.ExpenseService;
import com.UserService.UserService.service.OCRService;
import com.UserService.UserService.service.ReceiptAIService;



@RestController
@RequestMapping("/api/receipt")
@CrossOrigin(origins = "http://localhost:5173")
public class ReceiptController {



    private final ExpenseService expenseService;

    private final ReceiptAIService receiptAIService;

    private final OCRService ocrService;



    public ReceiptController(
            ExpenseService expenseService,
            ReceiptAIService receiptAIService,
            OCRService ocrService
    ){

        this.expenseService = expenseService;
        this.receiptAIService = receiptAIService;
        this.ocrService = ocrService;

    }





    @PostMapping("/scan")
    public String scanReceipt(
            @RequestParam("image")
            MultipartFile image
    ){


        try {


            File file =
                    File.createTempFile(
                            "receipt",
                            ".jpg"
                    );


            image.transferTo(file);



            return ocrService.extractText(file);


        }
        catch(Exception e){


            throw new RuntimeException(
                    "Upload failed"
            );

        }

    }






    @PostMapping("/create-expense")
    public ExpenseEntity createExpenseFromReceipt(


            @RequestParam("image")
            MultipartFile image,


            @RequestHeader("Authorization")
            String authHeader


    ){


        try {


            File file =
                    File.createTempFile(
                            "receipt",
                            ".jpg"
                    );


            image.transferTo(file);



            String text =
                    ocrService.extractText(file);




            Double amount =
                    receiptAIService.extractAmount(text);



            String description =
                    receiptAIService.extractDescription(text);




            Expensedto request =
                    new Expensedto();



            request.setAmount(amount);


            request.setDescription(description);


            request.setExpenseDate(
                    LocalDate.now()
            );




            String token =
                    authHeader.substring(7);




            return expenseService
                    .addExpenseFromReceipt(
                            request,
                            token
                    );


        }
        catch(Exception e){


            throw new RuntimeException(
                    "Receipt processing failed"
            );

        }

    }

}