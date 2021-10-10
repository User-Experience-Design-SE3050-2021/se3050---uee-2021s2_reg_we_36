package com.example.schoolmanagment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagment.R;
import com.example.schoolmanagment.modal.Fee;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class feeAdapter extends RecyclerView.Adapter<feeAdapter.FeeHolder> {
    private  Context context;
    private List<Fee> fees;

    public  feeAdapter(Context context, List<Fee> fees) {
        this.context = context;
        this.fees = fees;
    }

    @NonNull
    @Override
    public FeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new FeeHolder(LayoutInflater.from(context)
        .inflate(R.layout.fee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull feeAdapter.FeeHolder holder, int position) {
        holder.rollNum.setText(new StringBuffer().append(fees.get(position).getRollNum()));
        holder.month.setText(new StringBuffer().append(fees.get(position).getMonth()));
        holder.date.setText(new StringBuffer().append(fees.get(position).getDate()));
        holder.amount.setText(new StringBuffer().append(fees.get(position).getAmount()));
        holder.account.setText(new StringBuffer().append(fees.get(position).getAccountType()));

    }


    @Override
    public int getItemCount() {
        return fees.size();
    }
    public class FeeHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        @BindView(R.id.rollNum)
        TextView rollNum;
        @BindView(R.id.fMonth)
        EditText month;
        @BindView(R.id.fDate)
        EditText date;
        @BindView(R.id.fAmount)
        EditText amount;
        @BindView(R.id.fAccount)
        EditText account;


        private Unbinder unbinder;

        public FeeHolder(@NonNull View resultView) {
            super(resultView);
            unbinder = ButterKnife.bind(this,resultView);
            resultView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
