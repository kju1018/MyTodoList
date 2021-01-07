package com.kmsapp.mytodolist.Model;

import java.util.ArrayList;
import java.util.List;

public class Todo {
    private String contents;
    //    private LocalDate date;
//    private LocalTime time;
    private String date;
    private String time;
    private boolean isRepeat;
    private List repeatDay;
    private List repeatDayEn;


    public Todo() {
    }

    public Todo(String contents, String date, String time, boolean isRepeat, List repeatDay, List repeatDayEn) {
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatDay = repeatDay;
        this.repeatDayEn = repeatDayEn;
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

    public List getRepeatDay() {
        return repeatDay;
    }

    public void setRepeatDay(List repeatDay) {
        this.repeatDay = repeatDay;
    }

    public List getRepeatDayEn() {
        return repeatDayEn;
    }

    public void setRepeatDayEn(List repeatDayEn) {
        this.repeatDayEn = repeatDayEn;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isRepeat=" + isRepeat +
                ", repeatDay=" + repeatDay +
                ", repeatDayEn=" + repeatDayEn +
                '}';
    }
}
