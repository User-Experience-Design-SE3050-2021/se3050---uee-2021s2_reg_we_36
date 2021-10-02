package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Optionwindow extends AppCompatActivity {

    Button teach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionwindow);
        getSupportActionBar().hide();

        teach = findViewById(R.id.btnteach);

        teach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TeacherLogin.class);
                startActivity(i);
            }
        });
    }
}