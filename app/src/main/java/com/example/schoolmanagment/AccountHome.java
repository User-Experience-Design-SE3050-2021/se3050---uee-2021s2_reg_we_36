package com.example.schoolmanagment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.schoolmanagment.modal.User;
import com.example.schoolmanagment.utill.NotifyDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountHome extends AppCompatActivity {
    NotifyDialog notifyDialog;
    Button account, transaction, fee;
    User cus = new User();

    @BindView(R.id.imageView25)
    ImageView imageView20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);
        ButterKnife.bind(this);
        account = findViewById(R.id.btnacccc);
        transaction = findViewById(R.id.btntran);
        notifyDialog = new NotifyDialog(this);
        fee = findViewById(R.id.btnFee);
        getUser();
        account.setOnClickListener(view -> {
            Intent i = new Intent(AccountHome.this, ViewAccount.class);
            startActivity(i);
        });
        imageView20.setOnClickListener(view -> {
            Intent i = new Intent(AccountHome.this, AdminProfile.class);
            startActivity(i);
        });

        transaction.setOnClickListener(view -> {
            Intent i = new Intent(AccountHome.this, Transaction.class);
            startActivity(i);
        });

        fee.setOnClickListener(view -> {
            Intent i = new Intent(AccountHome.this, ViewPaidStudents.class);
            startActivity(i);
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountHome.this, AccountantProfile.class);
                startActivity(i);
            }
        });

    }

    private void getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid().toString();

        try {
            FirebaseDatabase.getInstance().getReference().child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        cus = snapshot.getValue(User.class);
                        Glide.with(AccountHome.this).load((Uri.parse(cus.getProPic()))).load(imageView20);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    notifyDialog.showNotifyDialog("error", "Data base error ! \n" + error.getMessage());
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}