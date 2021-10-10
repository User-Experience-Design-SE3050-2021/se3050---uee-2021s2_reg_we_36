package com.example.schoolmanagment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.schoolmanagment.modal.User;
import com.example.schoolmanagment.utill.NotifyDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherLogin extends AppCompatActivity {
    @BindView(R.id.techLoginbtn)
    Button loginBbtn;
    @BindView(R.id.editTextuserlogName)
    EditText editTextuserlogName;
    @BindView(R.id.editTextuserlogPassword)
    EditText editTextuserlogPassword;
    @BindView(R.id.textlogsign)
    TextView textlogsign;
    @BindView(R.id.frogetPass)
    TextView frogetPass;

    FirebaseAuth fAuth;
    NotifyDialog notifyDialog;
    User cus = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fAuth = FirebaseAuth.getInstance();
        ActivityCompat.requestPermissions(TeacherLogin.this, new String[]{Manifest.permission.INTERNET}, 1);
        ActivityCompat.requestPermissions(TeacherLogin.this, new String[]{Manifest.permission.WAKE_LOCK}, 1);

        notifyDialog = new NotifyDialog(this);


        loginBbtn.setOnClickListener(view -> {
            final String userEmail = editTextuserlogName.getText().toString().trim();
            final String userPassword = editTextuserlogPassword.getText().toString().trim();

            if (TextUtils.isEmpty(userEmail)) {
                editTextuserlogName.setError("Email is Required.");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                editTextuserlogName.setError("Enter valid email address.");
                return;
            }
            if (TextUtils.isEmpty(userPassword)) {
                editTextuserlogPassword.setError("password is Required.");
                return;
            }


            signIn(userEmail, userPassword);

        });
        frogetPass.setOnClickListener(v -> frogetPass(v));
        textlogsign.setOnClickListener(v -> startActivity(new Intent(TeacherLogin.this, Registration.class)));

    }

    private void signIn(String userEmail, String userPassword) {
        fAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(TeacherLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TeacherLogin.this, TeacherHome.class));
                getUser();
            }

        }).addOnFailureListener(e -> {
            notifyDialog.showNotifyDialog("error", "User name or password incorrect ! ");
            Toast.makeText(TeacherLogin.this, "User name or password incorrect ! " + e, Toast.LENGTH_SHORT).show();
        });


    }

    private void getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid().toString();
        notifyDialog.showNotifyDialog("", "Logged in Successfully");

        try {
            FirebaseDatabase.getInstance().getReference().child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        cus = snapshot.getValue(User.class);
                        if (cus.getType().equals("Teacher")) {
                            startActivity(new Intent(TeacherLogin.this, TeacherHome.class));
                        } else if (cus.getType().equals("Accountant")) {
                            startActivity(new Intent(TeacherLogin.this, AccountHome.class));
                        } else if (cus.getType().equals("Admin")) {
                            startActivity(new Intent(TeacherLogin.this, AdminHome.class));
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

    private void frogetPass(View v) {
        final EditText resetMail = new EditText(v.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // extract the email and send reset link
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        notifyDialog.showNotifyDialog("", "Sign in success");
                        Toast.makeText(TeacherLogin.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        notifyDialog.showNotifyDialog("error", "Sign in Fail");
                        Toast.makeText(TeacherLogin.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close the dialog
                dialog.dismiss();
            }
        });

        passwordResetDialog.create().show();

    }

}