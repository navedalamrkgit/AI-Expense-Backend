package com.UserService.UserService.service;


import java.io.File;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;


@Service
public class OCRService {



    public String extractText(File file){


        ITesseract tesseract = new Tesseract();


        tesseract.setDatapath(
            "C:/Program Files/Tesseract-OCR/tessdata"
        );


        tesseract.setLanguage("eng");


        // Improve receipt scanning
        tesseract.setPageSegMode(6);
        tesseract.setOcrEngineMode(1);



        try {


            String text =
                    tesseract.doOCR(file);



            System.out.println(
                    "OCR TEXT =====>"
            );


            System.out.println(text);



            return text;



        }
        catch(Exception e){


            throw new RuntimeException(
                    "OCR failed",
                    e
            );

        }

    }
}