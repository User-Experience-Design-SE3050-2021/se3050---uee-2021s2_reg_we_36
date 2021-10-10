package com.example.schoolmanagment.listner;

import com.example.schoolmanagment.modal.Fee;

import java.util.List;

public interface IfeeListner {
    void onResultLoadSuccess(List<Fee> results);
    void onResultLoadFail(String message);
}
