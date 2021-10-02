package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView logo, image;
    TextView sys, school, welcm, design;
    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

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



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,RegLogin.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}