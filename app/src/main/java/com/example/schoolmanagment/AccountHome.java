package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AccountHome extends AppCompatActivity {

    Button account,transaction, fee;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);
        getSupportActionBar().hide();

        account = findViewById(R.id.btnacccc);
        transaction = findViewById(R.id.btntran);
        fee = findViewById(R.id.btnFee);
        profile = findViewById(R.id.profilePic);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountHome.this, ViewAccount.class);
                startActivity(i);
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountHome.this, Transaction.class);
                startActivity(i);
            }
        });

        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountHome.this, ViewPaidStudents.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountHome.this, AccountantProfile.class);
                startActivity(i);
            }
        });

    }
}