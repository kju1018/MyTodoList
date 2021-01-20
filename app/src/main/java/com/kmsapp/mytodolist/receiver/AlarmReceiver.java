package com.kmsapp.mytodolist.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.ui.activity.MainActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    private String TODO_BUNDLE = "todo_bundle";
    private String  TODO = "todo";

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Todo todo = new Todo();

        Bundle todoBundle = intent.getBundleExtra(TODO_BUNDLE);
        if(todoBundle != null){
            todo = todoBundle.getParcelable(TODO);
        }

        Log.d("asdf", "onReceive:dddd");
        if(todo.isRepeat()){
            LocalDate today = LocalDate.now();
            List repeatDay = todo.getRepeatDayEn();
            if (!repeatDay.contains(String.valueOf(today.getDayOfWeek()))) {
                Log.d("asdf", "onReceive: 오늘은 반복 날이 아니얏!");
                return;
            }
        }

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(
                new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

        );
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,101,intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("할 일 알림");
        builder.setContentText(todo.getContents());
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        manager.notify(1, builder.build());
    }
}