package com.example.barcodeattendancetaker;

import android.content.Intent;

import java.util.ArrayList;

public class AttendanceModel {
   private ArrayList<String> rollNumbers;
   private String date;

   public AttendanceModel(ArrayList<String> arrayList , String string){
       this.rollNumbers=arrayList;
       this.date=string;
   }

   public ArrayList<String> getRollNumbers(){return rollNumbers;}
   public String getCount(){return Integer.toString(rollNumbers.size());
   }
   public String getDate(){return date;}
}
