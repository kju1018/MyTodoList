package com.kmsapp.mytodolist.Model;

public class Todo {
    private String content;
    private String date;
    private boolean repeat;
    private String priority;//우선순위

    public Todo() {
    }

    public Todo(String content, String date, boolean repeat, String priority) {
        this.content = content;
        this.date = date;
        this.repeat = repeat;
        this.priority = priority;
    }
}
