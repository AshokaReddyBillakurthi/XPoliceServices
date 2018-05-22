package com.xpoliceservices.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalenderUtils {


    public static String getCurrentDate(){
        String currentDate = "";
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            currentDate = formatter.format(date);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return currentDate;
    }
}
