package com.kmsapp.mytodolist.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.lang.reflect.GenericArrayType;
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
    private List<String> repeatDayKor;
    private List<String> repeatDayEn;
    private Timestamp timestamp;


    public Todo() {
    }

    public Todo(String contents, String date, String time, boolean isRepeat, String repeatComplete, List<String> repeatDayKor, List<String> repeatDayEn) {
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatComplete = repeatComplete;
        this.repeatDayKor = repeatDayKor;
        this.repeatDayEn = repeatDayEn;
    }

    public Todo(String todoId, String contents, String date, String time, boolean isRepeat, String repeatComplete, List<String> repeatDayKor, List<String> repeatDayEn) {
        this.todoId = todoId;
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatComplete = repeatComplete;
        this.repeatDayKor = repeatDayKor;
        this.repeatDayEn = repeatDayEn;
    }

    public Todo(String todoId, String completeId, String contents, String date, String time, boolean isRepeat, String repeatComplete, List<String> repeatDayKor, List<String> repeatDayEn) {
        this.todoId = todoId;
        this.completeId = completeId;
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isRepeat = isRepeat;
        this.repeatComplete = repeatComplete;
        this.repeatDayKor = repeatDayKor;
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

    public List<String> getRepeatDayKor() {
        return repeatDayKor;
    }

    public void setRepeatDayKor(List<String> repeatDayKor) {
        this.repeatDayKor = repeatDayKor;
    }

    public List<String> getRepeatDayEn() {
        return repeatDayEn;
    }

    public void setRepeatDayEn(List<String> repeatDayEn) {
        this.repeatDayEn = repeatDayEn;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todoId='" + todoId + '\'' +
                ", completeId='" + completeId + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isRepeat=" + isRepeat +
                ", repeatComplete='" + repeatComplete + '\'' +
                ", repeatDay=" + repeatDayKor +
                ", repeatDayEn=" + repeatDayEn +
                '}';
    }
}
