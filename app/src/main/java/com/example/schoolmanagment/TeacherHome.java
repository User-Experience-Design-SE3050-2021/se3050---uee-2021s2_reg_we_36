package com.example.schoolmanagment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class TeacherHome extends AppCompatActivity {
    NotifyDialog notifyDialog;
    Button examView,addstd;
    @BindView(R.id.imageView24)
    ImageView imageView20;
    @BindView(R.id.btnacccc)
    Button btnacccc;
    @BindView(R.id.button3)
    Button button3;


    User cus = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        ButterKnife.bind(this);
        notifyDialog = new NotifyDialog(this);
        examView = findViewById(R.id.btnexam);
        addstd = findViewById(R.id.student);
        getUser();
        button3.setOnClickListener(view -> startActivity(new Intent(TeacherHome.this,ViewClasses.class)));
        btnacccc.setOnClickListener(view -> startActivity(new Intent(TeacherHome.this,StudentAttendView.class)));
        examView.setOnClickListener(view -> {
            Intent i = new Intent(TeacherHome.this, ResultVIew.class);
            startActivity(i);
        });
        imageView20.setOnClickListener(view -> {
            Intent i = new Intent(TeacherHome.this, AdminProfile.class);
            startActivity(i);
        });
        addstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TeacherHome.this, ViewStudents.class);
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
                    if(snapshot.exists()){

                             cus = snapshot.getValue(User.class);
                            Glide .with(TeacherHome.this).load((Uri.parse(cus.getProPic()))).load(imageView20);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    notifyDialog.showNotifyDialog("error","Data base error ! \n" +error.getMessage() );
                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}