package com.kmsapp.mytodolist.Interface;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

public interface Repeat_Listener {
    void loadDay(String strDayNumber, String strDayKor, List dayEn, List dayKor, SparseBooleanArray checkedItems);
}
