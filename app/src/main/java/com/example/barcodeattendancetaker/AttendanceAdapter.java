package com.example.barcodeattendancetaker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{

    private OnAttendanceListener mOnAttendanceListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dateTextView;
        public TextView countTextView;
        public ImageView imageView;
        OnAttendanceListener onAttendanceListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView ,OnAttendanceListener onAttendanceListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.onAttendanceListener=onAttendanceListener;
            dateTextView = itemView.findViewById(R.id.date);
            countTextView = itemView.findViewById(R.id.count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAttendanceListener.onAttendanceClick(getAdapterPosition());
        }

    }

    public interface OnAttendanceListener{
        void onAttendanceClick(int position);
    }

    private List<AttendanceModel> mAttendance;

    // Pass in the contact array into the constructor
    public AttendanceAdapter(List<AttendanceModel> attendance,OnAttendanceListener onAttendanceListener) {
        mAttendance = attendance;
        this.mOnAttendanceListener=onAttendanceListener;
    }
    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View noteView = inflater.inflate(R.layout.list_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(noteView,mOnAttendanceListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceModel attendanceModel = mAttendance.get(position);
        TextView date = holder.dateTextView;
          date.setText(attendanceModel.getDate());
          TextView attendance = holder.countTextView;
          attendance.setText("Students Present : "+attendanceModel.getCount());
    }

    @Override
    public int getItemCount() {
        return mAttendance.size();
    }
    public List<AttendanceModel> getData() {
        return mAttendance;
    }
}
