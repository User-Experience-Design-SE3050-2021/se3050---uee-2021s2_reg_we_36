package com.example.schoolmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolmanagment.modal.User;
import com.example.schoolmanagment.utill.NotifyDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 4000;
    Animation topAnim, bottomAnim;
    ImageView logo, image;
    TextView sys, school, welcm, design;
    User cus = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.imagetopsplash);
        logo = findViewById(R.id.imagelogosplash);
        sys = findViewById(R.id.systemsplash);
        school = findViewById(R.id.schoolsplash);
        welcm = findViewById(R.id.welcomsplash);
        design = findViewById(R.id.resplash);

        image.setAnimation(topAnim);
        welcm.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        school.setAnimation(bottomAnim);
        sys.setAnimation(bottomAnim);
        design.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                chkUser();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void chkUser(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, RegLogin.class));
        }else {
            getUser();
        }
        finish();

    }
    private void getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid().toString();
        NotifyDialog notifyDialog = new NotifyDialog(this);
        try {
            FirebaseDatabase.getInstance().getReference().child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        cus = snapshot.getValue(User.class);
                        if (cus.getType().equals("Teacher")) {
                            startActivity(new Intent(MainActivity.this, TeacherHome.class));
                        } else if (cus.getType().equals("Accountant")) {
                            startActivity(new Intent(MainActivity.this, AccountHome.class));
                        } else if (cus.getType().equals("Admin")) {
                            startActivity(new Intent(MainActivity.this, AdminHome.class));
                        }


                    } else {

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