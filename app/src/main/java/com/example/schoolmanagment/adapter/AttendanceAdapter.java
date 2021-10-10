package com.example.schoolmanagment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagment.R;
import com.example.schoolmanagment.modal.Attendance;
import com.example.schoolmanagment.modal.result;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AddResultHolder>{

    private Context context;
    private List<Attendance> results ;

    public AttendanceAdapter(Context context, List<Attendance> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public AddResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddResultHolder(LayoutInflater.from(context)
                .inflate(R.layout.attendance_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddResultHolder holder, int position) {
        holder.staffname.setText(new StringBuffer().append(results.get(position).getName()));
        if (!results.get(position).getAttendance().isEmpty()){
            if (results.get(position).getAttendance() == "true"){
                holder.checkBox.setChecked(true);
                holder.checkBox2.setChecked(false);
            }else{
                holder.checkBox.setChecked(false);
                holder.checkBox2.setChecked(true);
            }

        }
        else{
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateAtt("true",results.get(position).toString());
                }
            });
            holder.checkBox2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateAtt("false", results.get(position).toString());
                }
            });
        }

    }

    private void updateAtt(String aTrue,String key) {
        FirebaseDatabase.getInstance().getReference().child("Attendance").child(key).child("attendance").setValue(aTrue);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class AddResultHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        @BindView(R.id.staffname)
        TextView staffname;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.checkBox2)
        CheckBox checkBox2;

        private Unbinder unbinder;

        public AddResultHolder(@NonNull View resultView) {
            super(resultView);
            unbinder = ButterKnife.bind(this,resultView);
            resultView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
