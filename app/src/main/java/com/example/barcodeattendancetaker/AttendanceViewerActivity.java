package com.example.barcodeattendancetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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