package com.example.schoolmanagment.listner;

import com.example.schoolmanagment.modal.result;

import java.util.List;

public interface IresultListner {
    void onResultLoadSuccess(List<result> results);
    void onResultLoadFail(String message);
}
