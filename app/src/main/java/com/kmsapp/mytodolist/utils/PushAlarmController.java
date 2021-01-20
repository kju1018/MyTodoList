package com.kmsapp.mytodolist.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.receiver.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PushAlarmController {

    private Context context;
    private AlarmManager alarmManager;
    private String TODO_BUNDLE = "todo_bundle";
    private String  TODO = "todo";

    public PushAlarmController() {
    }

    public void setAlarm(Todo todo, Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent receiverIntent = new Intent(context, AlarmReceiver.class);

        Bundle todoBundle = new Bundle();
        todoBundle.putParcelable(TODO, todo);

        receiverIntent.putExtra(TODO_BUNDLE, todoBundle);

        int requestCode =  0;
        PendingIntent pendingIntent;
        Date datetime = null;
        long interval = 1000 * 60 * 60 *24;//24시간
        long nowTime = System.currentTimeMillis();//현재 시간
        long selectTime = 0;
        Calendar calendar = Calendar.getInstance();

        if (todo.isRepeat()){
            requestCode = Integer.parseInt(AlarmCodeUtil.repeatcode(todo.getTimestamp().toDate()));
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, receiverIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            int hour = Integer.parseInt(todo.getTime().substring(0,2));
            int minute = Integer.parseInt(todo.getTime().substring(3,5));

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 00);

            selectTime  = calendar.getTimeInMillis();// 선택 시간

            if(nowTime > selectTime){
                selectTime += interval;
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectTime,interval, pendingIntent);

        }else{//그냥 일정이라면
            requestCode  = Integer.parseInt(AlarmCodeUtil.schedulecode(todo.getDate(), todo.getTime()));
            pendingIntent = PendingIntent.getBroadcast(context, requestCode, receiverIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                datetime = format.parse(todo.getDate()+" "+todo.getTime());
                //선택한 날짜 String를 Date로
            } catch (ParseException e) {
                e.printStackTrace();
            }


            calendar.setTime(datetime);
            selectTime  = calendar.getTimeInMillis();// 선택 시간
            if(nowTime > selectTime){
                selectTime += interval;
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, selectTime, pendingIntent);
        }

    }

    public void cancelAlarm(Todo todo, Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(context, AlarmReceiver.class);

        int requestCode =  0;

        if (todo.isRepeat()){
            requestCode = Integer.parseInt(AlarmCodeUtil.repeatcode(todo.getTimestamp().toDate()));
        }else {
            requestCode  = Integer.parseInt(AlarmCodeUtil.schedulecode(todo.getDate(), todo.getTime()));
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, receiverIntent,
                0);

        alarmManager.cancel(pendingIntent);
    }

}
//todo  습관일때, Wake UP 해결
