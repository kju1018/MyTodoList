package com.kmsapp.mytodolist.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class AlarmCodeUtil {

    public static String schedulecode(String date, String time){
            String code = date.substring(5,7) + date.substring(8,10) + time.substring(0,2) + time.substring(3,5);
            return code;
    }

    public static String repeatcode(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String code = ""+(calendar.get(Calendar.MONTH)+1) + (calendar.get(Calendar.DAY_OF_MONTH)+ calendar.get(Calendar.HOUR_OF_DAY)
                +calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND));
        return code;
    }
}