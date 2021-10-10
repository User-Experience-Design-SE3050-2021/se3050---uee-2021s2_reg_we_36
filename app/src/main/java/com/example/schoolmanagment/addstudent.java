package com.example.schoolmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagment.modal.result;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class addstudent extends AppCompatActivity {


    @BindView(R.id.stdadbtn)
    Button btnSave;
    @BindView(R.id.edtstuNamead)
    EditText txtstdname;
    @BindView(R.id.editstdclasno)
    EditText txtstdclass;
    @BindView(R.id.edtstdsection)
    EditText txtsect;
    @BindView(R.id.edtstdsubj)
    EditText txtstdsub;
    @BindView(R.id.edtstdmark)
    EditText txtstdmark;
    @BindView(R.id.edtstdstate)
    EditText txtstdstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        ButterKnife.bind(this);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String studentName = txtstdname.getText().toString().trim();
                final String classNo = txtstdclass.getText().toString().trim();
                final String section = txtsect.getText().toString().trim();
                final String subject = txtstdsub.getText().toString().trim();
                final String mark = txtstdmark.getText().toString().trim();
                final String status = txtstdstate.getText().toString().trim();
                if (TextUtils.isEmpty(studentName)) {
                    txtstdname.setError("Name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(classNo)) {
                    txtstdclass.setError("class is Required.");
                    return;
                }
                if (TextUtils.isEmpty(section)) {
                    txtsect.setError("section is Required.");
                    return;
                }
                saveItem(studentName,classNo,section,subject,mark,status);
            }
        });
    }

    public void saveItem(String studentName,String classNo,String section,String subject,String mark,String status) {
        DatabaseReference reviewref = FirebaseDatabase.getInstance("https://ueeschoolmanagment-default-rtdb.firebaseio.com/").getReference("result");

        String key = reviewref.push().getKey();
        reviewref = FirebaseDatabase.getInstance("https://ueeschoolmanagment-default-rtdb.firebaseio.com/").getReference("result").child(key);

        result ret = new result();

        ret.setStudentName(studentName);
        ret.setClassNo(classNo);
        ret.setSection(section);
        ret.setSubject(subject);
        ret.setMark(mark);
        ret.setStatus(status);


        reviewref.setValue(ret)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this,"Item added success",Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,"Item added fail",Toast.LENGTH_SHORT).show();
                });
    }
}