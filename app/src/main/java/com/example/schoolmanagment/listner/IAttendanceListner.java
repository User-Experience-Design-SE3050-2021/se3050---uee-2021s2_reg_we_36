package com.example.schoolmanagment.listner;

import com.example.schoolmanagment.modal.Attendance;
import com.example.schoolmanagment.modal.result;

import java.util.List;

public interface IAttendanceListner {
    void onResultLoadSuccess(List<Attendance> results);
    void onResultLoadFail(String message);
}
