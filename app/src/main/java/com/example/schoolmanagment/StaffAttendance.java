package com.example.schoolmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.schoolmanagment.adapter.AttendanceAdapter;
import com.example.schoolmanagment.listner.IAttendanceListner;
import com.example.schoolmanagment.listner.IresultListner;
import com.example.schoolmanagment.modal.Attendance;
import com.example.schoolmanagment.modal.result;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaffAttendance extends AppCompatActivity implements IAttendanceListner {
    @BindView(R.id.recyclerView)
    RecyclerView result_recycle;
    //import back button
    @BindView(R.id.btnBack)
    ImageView backBtn;


    IAttendanceListner iresultListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        setContentView(R.layout.activity_student_attendance);
        //back button code
        init();
        loadItem();
        backBtn.setOnClickListener(view -> finish());
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
        List<Attendance> attendanceList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("result")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot resultSnapshot : snapshot.getChildren()){
                                result resul = resultSnapshot.getValue(result.class);
                                results.add(resul);
                                Attendance attendance = new Attendance();
                                attendance.setKey(resultSnapshot.getKey());
                                attendance.setName(resul.getStudentName());
                                attendance.setAttendance("");
                                attendanceList.add(attendance);

                            }
                            setAttendance(attendanceList);
                            iresultListner.onResultLoadSuccess(attendanceList);
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

    private void setAttendance(List<Attendance> attendance){
        FirebaseDatabase.getInstance().getReference().child("Attendance").setValue(attendance).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

    }
    @Override
    public void onResultLoadSuccess(List<Attendance> results) {
        AttendanceAdapter adapter = new AttendanceAdapter(this,results);
        result_recycle.setAdapter(adapter);
    }

    @Override
    public void onResultLoadFail(String message) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}