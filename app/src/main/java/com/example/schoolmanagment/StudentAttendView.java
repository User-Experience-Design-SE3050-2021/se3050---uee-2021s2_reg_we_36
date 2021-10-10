package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAttendView extends AppCompatActivity {
    //import back button
    @BindView(R.id.btnBack)
    ImageView backBtn;
    //date picker
    @BindView(R.id.shwDate)
    Button shwDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attend_view);

        //back button code
        ButterKnife.bind(this);
        backBtn.setOnClickListener(view -> finish());

        //date picker
        DatePicker datePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker

        datePicker.setSpinnersShown(false); // set false value for the spinner shown function

        //show hide date picker
        shwDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datePicker.getVisibility() == View.GONE){
                    datePicker.setVisibility(View.VISIBLE);
                }else{
                    datePicker.setVisibility(View.GONE);
                }
            }
        });
    }
}