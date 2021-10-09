package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AccountHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);
        getSupportActionBar().hide();

    }
}