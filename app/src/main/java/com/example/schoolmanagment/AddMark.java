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

import com.example.schoolmanagment.adapter.addResultAdapter;
import com.example.schoolmanagment.adapter.resultAdapter;
import com.example.schoolmanagment.listner.IresultListner;
import com.example.schoolmanagment.modal.result;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMark extends AppCompatActivity implements IresultListner{

    Button btnad ;
    @BindView(R.id.addRecle)
    RecyclerView addres_recycle;
    @BindView(R.id.addmain_const)
    ConstraintLayout resmain_layout;
    @BindView(R.id.spinneraddmark)
    Spinner spine;

    IresultListner iresultListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);

        btnad = findViewById(R.id.marksave);


        btnad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AddMark.this,ResultVIew.class);
                startActivity(i);
            }
        });

        init();
        loadItem();

    }

    public void init(){
        ButterKnife.bind(this);

        iresultListner = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        addres_recycle.setLayoutManager(linearLayoutManager);
        addres_recycle.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

    }
    private void loadItem() {
        List<result> results = new ArrayList<>();
        FirebaseDatabase.getInstance("https://ueeschoolmanagment-default-rtdb.firebaseio.com/").getReference("result")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot resultSnapshot : snapshot.getChildren()){
                                result resul = resultSnapshot.getValue(result.class);
                                results.add(resul);
                            }
                            iresultListner.onResultLoadSuccess(results);
                        }else{
                            iresultListner.onResultLoadFail("Cant find items");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iresultListner.onResultLoadFail(error.getMessage());
                    }
                });
    }

    @Override
    public void onResultLoadSuccess(List<result> results) {
        addResultAdapter adapter = new addResultAdapter(this,results);
        addres_recycle.setAdapter(adapter);
    }

    @Override
    public void onResultLoadFail(String message) {
        Snackbar.make(resmain_layout,message,Snackbar.LENGTH_LONG).show();
    }
}