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
import com.example.schoolmanagment.modal.result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class addResultAdapter extends RecyclerView.Adapter<addResultAdapter.AddResultHolder>{

    private Context context;
    private List<result> results ;

    public addResultAdapter(Context context, List<result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public AddResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddResultHolder(LayoutInflater.from(context)
                .inflate(R.layout.addresults,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddResultHolder holder, int position) {
        holder.txtName.setText(new StringBuffer().append(results.get(position).getStudentName()));
        holder.edtmark.setText(new StringBuffer().append(results.get(position).getMark()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class AddResultHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        @BindView(R.id.stdname)
        TextView txtName;
        @BindView(R.id.editmarkstd)
        EditText edtmark;

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
