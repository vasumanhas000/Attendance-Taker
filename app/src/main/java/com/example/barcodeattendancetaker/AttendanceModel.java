package com.example.barcodeattendancetaker;

import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceModel {
   private ArrayList<String> rollNumbers;
   private String date;



   public AttendanceModel(ArrayList<String> arrayList , String string){
       this.rollNumbers=arrayList;
       SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
       try {
           Date dateObj = curFormater.parse(string);
           SimpleDateFormat postFormater = new SimpleDateFormat("dd MMMM, yyyy");
           String newDateStr = postFormater.format(dateObj);
           this.date=newDateStr;
       }catch (Exception e){
           e.printStackTrace();
       }

   }

   public ArrayList<String> getRollNumbers(){return rollNumbers;}
   public String getCount(){return Integer.toString(rollNumbers.size());
   }
   public String getDate(){return date;}
}
