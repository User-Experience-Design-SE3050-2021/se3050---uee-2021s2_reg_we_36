package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewClasses extends AppCompatActivity {
    //import back button
    @BindView(R.id.btnBack)
    ImageView backBtn;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);

        add = findViewById(R.id.floatingActionBtb);
        //back button code
        ButterKnife.bind(this);
        backBtn.setOnClickListener(view -> finish());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddClassActivity.class);
                startActivity(i);
            }
        });
    }
}