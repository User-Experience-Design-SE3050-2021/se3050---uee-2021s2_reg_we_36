package com.example.schoolmanagment.utill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schoolmanagment.R;


public class NotifyDialog {
    private Activity activity;
    private AlertDialog dialog;

    public NotifyDialog(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("ResourceAsColor")
    public void showNotifyDialog(String type, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.notification_layout,null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.message)).setText(message);

        if(type == "error"){
            ((ImageView) view.findViewById(R.id.dialogImage)).setImageResource(R.drawable.ic_component_50);
            ((TextView) view.findViewById(R.id.message)).setTextColor(R.color.design_default_color_error);
        }else {
            ((ImageView) view.findViewById(R.id.dialogImage)).setImageResource(R.drawable.ic_component_51);
            ((TextView) view.findViewById(R.id.message)).setTextColor(R.color.purple_500);
        }

        dialog = builder.create();
        dialog.show();
    }

    public void dismissNotifyDialog(){
        dialog.dismiss();
    }
}
