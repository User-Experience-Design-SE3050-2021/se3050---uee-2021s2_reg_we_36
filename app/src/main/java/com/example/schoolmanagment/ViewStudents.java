package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStudents extends AppCompatActivity {


    FloatingActionButton add;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        ButterKnife.bind(this);
        add = findViewById(R.id.floatingActionBtb);

        add.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), addstudent.class);
            startActivity(i);
        });
        btnBack.setOnClickListener(view -> finish());
    }
}