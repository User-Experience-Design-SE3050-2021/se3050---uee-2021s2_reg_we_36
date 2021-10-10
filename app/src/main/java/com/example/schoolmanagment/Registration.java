package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.schoolmanagment.modal.User;
import com.example.schoolmanagment.utill.LoadingDialog;
import com.example.schoolmanagment.utill.NotifyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Registration extends AppCompatActivity {
    @BindView(R.id.Regisbtn)
    Button reg;
    @BindView(R.id.userType)
    Spinner userType;
    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.conpassword)
    EditText conpassword;
    @BindView(R.id.picImage)
    ImageView picImage;
    @BindView(R.id.gotoSignIn)
    TextView gotoSignIn;

    LoadingDialog loadingDialog;
    NotifyDialog notifyDialog;
    FirebaseAuth fAuth;
    String userID;
    User cus = new User();
    Uri imguri,downUri;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        notifyDialog = new NotifyDialog(this);
        loadingDialog = new LoadingDialog(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storageRef = FirebaseStorage.getInstance().getReference("proPic");
        fAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.selectusertype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        picImage.setOnClickListener(v -> FileChooser());
        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,RegLogin.class));
            }
        });
        reg.setOnClickListener(view -> {
            final String conpass = conpassword.getText().toString().trim();
            final String name = fullName.getText().toString().trim();
            final String mail = email.getText().toString().trim();
            final String passw = password.getText().toString().trim();
            final String type = userType.getSelectedItem().toString();
            if (TextUtils.isEmpty(name)) {
                fullName.setError("Name is Required.");
                return;
            }    if (TextUtils.isEmpty(mail)) {
                email.setError("email is Required.");
                return;
            }    if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.setError("Enter valid email address.");
                return;
            }
            if (TextUtils.isEmpty(type)) {
                ((TextView)userType.getSelectedView()).setError("Select user type");
                return;
            }
            if (TextUtils.isEmpty(passw)) {
                password.setError("Password is Required.");
                return;
            }
            if (TextUtils.isEmpty(conpass)) {
                conpassword.setError("Please confirm password.");
                return;
            }
            if (!conpass.equals(passw)) {
                conpassword.setError("Password mismatch.");
                return;
            }

            register(name,mail,passw,type);

        });
    }
    private void register(String name, String mail, String pass, String type) {
        loadingDialog.startLoadingDialog();
        fAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // send verification link

                FirebaseUser fuser = fAuth.getCurrentUser();
                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Registration.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        userRegister(name,mail,pass,type);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        notifyDialog.showNotifyDialog("error","Sign up fail" );
                    }
                });
                loadingDialog.dismissLoadingDialog();
            } else {
                Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * register user
     * @param name
     * @param mail
     * @param passw
     */
    private void userRegister(String name, String mail, String passw, String type) {
        if (downUri != null){
            cus.setProPic(downUri.toString());
        }else{
            Toast.makeText(this, "Please wait image is uploading",Toast.LENGTH_SHORT).show();
            return;
        }
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");


        cus.setEmail(mail);
        cus.setName(name);
        cus.setType(type);
        cus.setPassword(passw);
        cus.setProPic(downUri.toString());
        cus.setKey(userID);

        ref.child(userID).setValue(cus).addOnSuccessListener(unused -> {
            Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();

            if (type.equals("Teacher")){
               startActivity(new Intent(Registration.this,TeacherHome.class));
            }else if (type.equals("Accountant")){
                startActivity(new Intent(Registration.this,AccountHome.class));
            }else if (type.equals("Admin")){
                startActivity(new Intent(Registration.this,AdminHome.class));
            }

        }).addOnFailureListener(e -> {
            notifyDialog.showNotifyDialog("error","User profile create fail");
        });


    }

    /**
     * get file chooser value
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            imguri =data.getData();
            Glide.with(Registration.this).load(imguri).into(picImage);
            Toast.makeText(this,imguri.toString(),Toast.LENGTH_SHORT).show();
            FileUploader();

        }
        else Toast.makeText(this, "You Haven't Select Image", Toast.LENGTH_SHORT).show();
    }

    /***
     * upload user profile pic
     * @param uri
     * @return
     */
    public String  getExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void FileUploader() {
        StorageReference ref = storageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
        UploadTask uploadTask = ref.putFile(imguri);
        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                downUri = task.getResult();
            } else {

            }
        });
    }

    /**
     * open file chooser
     */
    public void FileChooser() {
        Intent photoPicker=new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);

    }

}