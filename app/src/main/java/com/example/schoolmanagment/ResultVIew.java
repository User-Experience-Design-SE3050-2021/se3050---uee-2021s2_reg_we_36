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
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.schoolmanagment.adapter.resultAdapter;
import com.example.schoolmanagment.eventBus.UpdateResult;
import com.example.schoolmanagment.listner.IresultListner;
import com.example.schoolmanagment.modal.result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultVIew extends AppCompatActivity implements IresultListner{
    @BindView(R.id.result_recycle)
    RecyclerView result_recycle;
    @BindView(R.id.mainlay)
    ConstraintLayout main_layout;
    @BindView(R.id.spinnerresutview)
    Spinner spine;
    @BindView(R.id.btnBack)
    ImageView btnBack;

    FloatingActionButton fbt;

    IresultListner iresultListner;


//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(EventBus.getDefault().hasSubscriberForEvent(UpdateResult.class))
//            EventBus.getDefault().removeStickyEvent(UpdateResult.class);
//        EventBus.getDefault().unregister(this);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);


        fbt = findViewById(R.id.floatingActionBtb);

        fbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(ResultVIew.this,PopupClass.class);
                startActivity(i);
            }
        });

        init();
        loadItem();
        btnBack.setOnClickListener(view -> finish());

    }


    public void init(){
        ButterKnife.bind(this);

        iresultListner = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        result_recycle.setLayoutManager(linearLayoutManager);
        result_recycle.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

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
        resultAdapter adapter = new resultAdapter(this,results);
        result_recycle.setAdapter(adapter);
    }

    @Override
    public void onResultLoadFail(String message) {
        Snackbar.make(main_layout,message,Snackbar.LENGTH_LONG).show();
    }
}