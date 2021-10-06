package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherHome extends AppCompatActivity {

    Button examView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        getSupportActionBar().hide();

        examView = findViewById(R.id.btnexam);

        examView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TeacherHome.this, ResultVIew.class);
                startActivity(i);
            }
        });

    }
}