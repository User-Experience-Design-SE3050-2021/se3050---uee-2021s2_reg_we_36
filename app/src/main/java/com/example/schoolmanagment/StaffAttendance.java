package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaffAttendance extends AppCompatActivity {
    //import back button
    @BindView(R.id.btnBack)
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(view -> finish());
    }
}