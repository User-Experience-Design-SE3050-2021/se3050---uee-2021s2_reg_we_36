package com.example.schoolmanagment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagment.R;
import com.example.schoolmanagment.listner.IrecycleClickListner;
import com.example.schoolmanagment.modal.result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.ResultHolder> {

    private Context context;
    private List<result> results ;

    public resultAdapter(Context context, List<result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultHolder(LayoutInflater.from(context)
                .inflate(R.layout.result,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        holder.txtName.setText(new StringBuffer().append(results.get(position).getStudentName()));
        holder.txtStatus.setText(new StringBuffer().append(results.get(position).getStatus()));
        holder.edtsubj.setText(new StringBuffer().append(results.get(position).getSubject()));
        holder.edtmark.setText(new StringBuffer().append(results.get(position).getMark()));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ResultHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        @BindView(R.id.stnametxt)
        TextView txtName;
        @BindView(R.id.stattxt)
        TextView txtStatus;
        @BindView(R.id.editTextsubj)
        EditText edtsubj;
        @BindView(R.id.editTextMark)
        EditText edtmark;


        private Unbinder unbinder;

        public ResultHolder(@NonNull View resultView) {
            super(resultView);
            unbinder = ButterKnife.bind(this,resultView);
            resultView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
