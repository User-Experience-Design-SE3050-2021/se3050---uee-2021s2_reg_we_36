package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class AdminHome extends AppCompatActivity {
    //import back button
    @BindView(R.id.btnacccc)
    Button btnacccc;
    @BindView(R.id.btntran)
    Button btntran;
    NotifyDialog notifyDialog;
    User cus = new User();

    @BindView(R.id.imageView25)
    ImageView imageView20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);
        notifyDialog = new NotifyDialog(this);
        btnacccc.setOnClickListener(view -> startActivity(new Intent(AdminHome.this,StaffAttendance.class)));
        btntran.setOnClickListener(view -> startActivity(new Intent(AdminHome.this,ViewStaff.class)));
        getUser();
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
                        Glide.with(AdminHome.this).load((Uri.parse(cus.getProPic()))).load(imageView20);

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