package com.kmsapp.mytodolist.Interface;

import com.applandeo.materialcalendarview.EventDay;

import java.util.List;

public interface FirebaseListener {
    void onSuccess(List<EventDay> eventDays);
}
