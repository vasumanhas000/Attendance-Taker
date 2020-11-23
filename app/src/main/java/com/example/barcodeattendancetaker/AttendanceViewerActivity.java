package com.example.barcodeattendancetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AttendanceViewerActivity extends AppCompatActivity {

    int position;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void onClickDelete(View view){
        MainActivity.attendanceModels.remove(position);
        MainActivity.adapter.notifyDataSetChanged();
        try {
            Gson gson = new Gson();
            String response = gson.toJson(MainActivity.attendanceModels);
            MainActivity.sharedPreferences.edit().putString("attendance",response).apply();
            Log.i("add attendance", "attendance: added");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(MainActivity.attendanceModels.size()==0){
            MainActivity.recyclerView.setVisibility(View.GONE);
            MainActivity.constraintLayout.setVisibility(View.VISIBLE);
        }
        else{
            MainActivity.recyclerView.setVisibility(View.VISIBLE);
            MainActivity.constraintLayout.setVisibility(View.GONE);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_viewer);
        Intent intent =getIntent();
        position = intent.getIntExtra("position",-1);
        arrayList= MainActivity.attendanceModels.get(position).getRollNumbers();
        listView= findViewById(R.id.attendance_list_view);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.attendance_list_layout,R.id.attendance_list_text_view,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}