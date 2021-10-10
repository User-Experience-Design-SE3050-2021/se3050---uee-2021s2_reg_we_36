package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.schoolmanagment.adapter.feeAdapter;
import com.example.schoolmanagment.listner.IfeeListner;
import com.example.schoolmanagment.modal.Fee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPaidStudents extends AppCompatActivity implements IfeeListner {

    @BindView(R.id.fee_recycle)
    RecyclerView fee_recycle;
    @BindView(R.id.mainLayout)
    ConstraintLayout main_layout;


    Button type;

    FloatingActionButton fbt;

    IfeeListner ifeeListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paid_students);

        fbt = findViewById(R.id.floatingActionBtb);
        type = findViewById(R.id.type);

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewPaidStudents.this, NonPaidStudents.class);
                startActivity(i);
            }
        });

        fbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewPaidStudents.this, AddFee.class);
                startActivity(i);
            }
        });
        init();
        loadItem();
    }

    public void init(){
        ButterKnife.bind(this);

        ifeeListner = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fee_recycle.setLayoutManager(linearLayoutManager);
        fee_recycle.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

    }

    private void loadItem() {
        List<Fee> fees = new ArrayList<>();
        FirebaseDatabase.getInstance("https://ueeschoolmanagment-default-rtdb.firebaseio.com/").getReference("Fee")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot resultSnapshot : snapshot.getChildren()) {
                                Fee fee = resultSnapshot.getValue(Fee.class);
                                fees.add(fee);
                            }
                            ifeeListner.onResultLoadSuccess(fees);
                        } else {
                            ifeeListner.onResultLoadFail("Cant find items");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        ifeeListner.onResultLoadFail(error.getMessage());
                    }


    });
    }
    @Override
    public void onResultLoadSuccess(List<Fee> fees) {
        feeAdapter adapter = new feeAdapter(this,fees);
        fee_recycle.setAdapter(adapter);
    }

    @Override
    public void onResultLoadFail(String message) {
        Snackbar.make(main_layout,message,Snackbar.LENGTH_LONG).show();
    }
    }