package com.kmsapp.mytodolist.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Queue;

public class Todo {
    private String contents;
    //    private LocalDate date;
//    private LocalTime time;
    private String date;
    private String time;
    private boolean isRepeat;
    private Queue<String> repeatDay;


    public Todo() {
    }

    public Todo(String contents, String date, String time, boolean isRepeat, Queue<String> repeatDay) {
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatDay = repeatDay;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public Queue<String> getRepeatDay() {
        return repeatDay;
    }

    public void setRepeatDay(Queue<String> repeatDay) {
        this.repeatDay = repeatDay;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isRepeat=" + isRepeat +
                ", repeatDay=" + repeatDay +
                '}';
    }
}
