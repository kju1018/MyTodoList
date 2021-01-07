package com.kmsapp.mytodolist.Interface;

import android.util.SparseBooleanArray;

import java.util.List;

public interface Repeat_Listener {
    void loadDay(String dayNumber, String dayKor, List dayEn, SparseBooleanArray checkedItems);
}
