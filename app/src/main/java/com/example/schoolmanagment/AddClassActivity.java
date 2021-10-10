package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolmanagment.modal.Classes;
import com.example.schoolmanagment.utill.NotifyDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends AppCompatActivity {

    TextView cancel, save;

    @BindView(R.id.txtClassgrade)
    Spinner txtClassgrade;
    @BindView(R.id.txtClassName)
    EditText txtClassName;
    @BindView(R.id.txtTeachername)
    EditText txtTeachername;
    NotifyDialog notifyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        ButterKnife.bind(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

       notifyDialog = new NotifyDialog(this);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.80), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


        cancel = findViewById(R.id.canceltxt);
        save = findViewById(R.id.savetext);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(txtClassName.getText().toString())){
                    txtClassName.setError("Class name is required.");
                    return;
                }  if (TextUtils.isEmpty(txtClassgrade.getSelectedItem().toString())) {
                    ((TextView)txtClassgrade.getSelectedView()).setError("Select class grade");
                    return;
                } if(TextUtils.isEmpty(txtTeachername.getText().toString())){
                    txtTeachername.setError("Class teacher name is required.");
                    return;
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Class");
                String key = ref.push().getKey();

                Classes classes = new Classes();
                classes.setName(txtClassName.getText().toString());
                classes.setTeacherName(txtTeachername.getText().toString());
                classes.setGrade(txtClassgrade.getSelectedItem().toString());
                classes.setStudentCount("23");
                classes.setKey(key);



                ref.child(key).setValue(classes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        notifyDialog.showNotifyDialog("","Successfully class added");
                        Intent i = new Intent(getApplicationContext(), ViewClasses.class);
                        startActivity(i);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        notifyDialog.showNotifyDialog("error","Class add fail \n" + e.getMessage());

                    }
                });
            }
        });


    }
}