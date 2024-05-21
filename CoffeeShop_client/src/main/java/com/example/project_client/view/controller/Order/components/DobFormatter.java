package com.example.project_client.view.controller.Order.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DobFormatter {

    public static String toString(LocalDate dob){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dob.format(formatter);
    }
    public static LocalDate toDate(String dob){
        if(dob==null||dob.isEmpty()) return LocalDate.now();
        return LocalDate.parse(dob);
    }
}
