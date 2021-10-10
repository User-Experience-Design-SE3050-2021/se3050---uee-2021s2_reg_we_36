package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolmanagment.modal.Fee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFee extends AppCompatActivity {


    @BindView(R.id.sRollNum)
    EditText rollNum;
    @BindView(R.id.sAmount)
    EditText amount;
    @BindView(R.id.sDate)
    EditText date;
    @BindView(R.id.submitbtn)
    Button btn;
    @BindView(R.id.monthSelect)
    Spinner month;
    @BindView(R.id.accountSelect)
    Spinner account;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee);

        ButterKnife.bind(this);
        Fee fee = new Fee();

        btn.setOnClickListener(view -> {

            dbRef = FirebaseDatabase.getInstance().getReference().child("Fee");

            String rollNumber = rollNum.getText().toString();
            String feeAmount = amount.getText().toString();
            String feeDate = date.getText().toString();
            String Month = month.getSelectedItem().toString();
            String Account = account.getSelectedItem().toString();

            try {
                if (TextUtils.isEmpty(rollNumber)){
                    rollNum.setError("Please enter Roll Number");
                return;
            }
                 if (TextUtils.isEmpty(feeAmount)){
                     amount.setError("Please enter amount");
                     return;
                 }
                 if (TextUtils.isEmpty(feeDate)){
                     date.setError("Please enter Date");
                     return;
                 }


                String key = dbRef.push().getKey();
                fee.setKey(key);
                fee.setRollNum(rollNumber);
                fee.setAmount(feeAmount);
                fee.setDate(feeDate);
                fee.setMonth(Month);
                fee.setAccountType(Account);

                    dbRef.child(key).setValue(fee).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddFee.this, "Fee Details Successfully added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddFee.this, "Database inserting failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}