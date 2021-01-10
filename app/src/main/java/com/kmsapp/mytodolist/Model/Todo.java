package com.kmsapp.mytodolist.Model;

import java.util.ArrayList;
import java.util.List;

public class Todo {
    private String todoId;
    private String completeId;
    private String contents;
    private String date;
    private String time;
    private boolean isRepeat;
    private String repeatComplete;
    private List repeatDay;
    private List repeatDayEn;


    public Todo() {
    }

    public Todo(String contents, String date, String time, boolean isRepeat, String repeatComplete, List repeatDay, List repeatDayEn) {
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatComplete = repeatComplete;
        this.repeatDay = repeatDay;
        this.repeatDayEn = repeatDayEn;
    }

    public Todo(String todoId, String contents, String date, String time, boolean isRepeat, String repeatComplete, List repeatDay, List repeatDayEn) {
        this.todoId = todoId;
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatComplete = repeatComplete;
        this.repeatDay = repeatDay;
        this.repeatDayEn = repeatDayEn;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getCompleteId() {
        return completeId;
    }

    public void setCompleteId(String completeId) {
        this.completeId = completeId;
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

    public String getRepeatComplete() {
        return repeatComplete;
    }

    public void setRepeatComplete(String repeatComplete) {
        this.repeatComplete = repeatComplete;
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
                "todoId='" + todoId + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isRepeat=" + isRepeat +
                ", repeatComplete='" + repeatComplete + '\'' +
                ", repeatDay=" + repeatDay +
                ", repeatDayEn=" + repeatDayEn +
                '}';
    }
}
