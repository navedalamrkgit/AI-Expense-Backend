package com.UserService.UserService.service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class ReceiptAIService {



    public Double extractAmount(String text){



        // Priority: Total / Grand Total / Amount

        Pattern totalPattern =
                Pattern.compile(
                "(?i)(total|grand total|amount|net amount|payable).*?(\\d+\\.\\d+)"
                );



        Matcher matcher =
                totalPattern.matcher(text);



        if(matcher.find()){


            return Double.parseDouble(
                    matcher.group(2)
            );

        }




        // Fallback: find highest decimal value

        Pattern numberPattern =
                Pattern.compile(
                "\\d+\\.\\d+"
                );



        Matcher numberMatcher =
                numberPattern.matcher(text);



        double max = 0;



        while(numberMatcher.find()){


            double value =
                    Double.parseDouble(
                            numberMatcher.group()
                    );



            if(value > max){

                max = value;

            }

        }



        return max;

    }







    public String extractDescription(String text){



        String[] lines =
                text.split("\\n");



        String backup = null;



        for(String line : lines){



            line = line.trim();



            if(line.length() < 5){

                continue;

            }



            String lower =
                    line.toLowerCase();





            // Remove unwanted receipt sections

            if(lower.contains("hotel name")
                    || lower.contains("address")
                    || lower.contains("mobile")
                    || lower.contains("phone")
                    || lower.contains("gst")
                    || lower.contains("invoice")
                    || lower.contains("original")
                    || lower.contains("copy")
                    || lower.contains("page")
                    || lower.contains("date")
                    || lower.contains("total")
                    || lower.contains("amount")
                    || lower.contains("please note")
                    || lower.contains("signature")
                    || lower.contains("billing officer")
                    || lower.contains("guest")
                    || lower.contains("thank")
                    || lower.contains("in words")
                    || lower.contains("only")
                    || lower.contains("rs.")
                    || lower.contains("rs ")
                    || lower.contains("rupees")
            ){

                continue;

            }





            // Ignore lines containing numbers

            if(line.matches(".*\\d+.*")){

                continue;

            }





            // Ignore OCR garbage symbols

            if(line.contains("[")
                    || line.contains("]")
                    || line.contains("(")
                    || line.contains(")")
                    || line.contains("|")
                    || line.equals("-")
            ){

                continue;

            }






            if(backup == null){

                backup = line;

            }





            // Prefer useful description

            if(lower.contains("hotel")
                    || lower.contains("room")
                    || lower.contains("bill")
                    || lower.contains("restaurant")
            ){

                return line;

            }


        }






        if(backup != null){

            return backup;

        }





        return "Hotel Expense";

    }









    public String predictCategory(String description){



        String text =
                description.toLowerCase();





        if(text.contains("hotel")
                || text.contains("room")
                || text.contains("stay")
                || text.contains("hotel expense")
                || text.contains("bill")
        ){


            return "Travel";

        }






        if(text.contains("food")
                || text.contains("restaurant")
                || text.contains("cafe")
                || text.contains("pizza")
        ){


            return "Food";

        }







        if(text.contains("cab")
                || text.contains("taxi")
                || text.contains("uber")
        ){


            return "Transport";

        }






        return "Other";

    }



}