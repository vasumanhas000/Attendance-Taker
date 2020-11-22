package com.example.barcodeattendancetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView barcodeInfo;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    BottomSheetBehavior bottomSheetBehavior;

    public void onClickTick(View view){
        if(bottomSheetBehavior.getState()==bottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
        }else{
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            MainActivity.attendanceModels.add(new AttendanceModel(arrayList,date));
            MainActivity.adapter.notifyDataSetChanged();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        cameraView = findViewById(R.id.camera_view);
        barcodeInfo = findViewById(R.id.text_view);
        listView = findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        arrayList.add("Hello");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item,R.id.list_text_view,arrayList);
        listView.setDivider(null);
        listView.setAdapter(arrayAdapter);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(1280,720).setRequestedFps(30).build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {

//                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
//                        public void run() {
//                            Log.i("barcode",barcodes.valueAt(0).displayValue);
//                            barcodeInfo.setText(    // Update the TextView
//                                    barcodes.valueAt(0).displayValue
//                            );
//                        }
//                    });
                    if(!arrayList.contains(barcodes.valueAt(0).displayValue)){
                        Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibrator.vibrate(500);
                        }
                    arrayList.add(barcodes.valueAt(0).displayValue);
                   listView.post(new Runnable() {
                       @Override
                       public void run() {
                         arrayAdapter.notifyDataSetChanged();
                       }
                   });
                }}
            }
        });
        ConstraintLayout bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
         bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
    }
}