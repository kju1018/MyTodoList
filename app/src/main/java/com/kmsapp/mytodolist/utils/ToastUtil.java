package com.kmsapp.mytodolist.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void toastPrint(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
