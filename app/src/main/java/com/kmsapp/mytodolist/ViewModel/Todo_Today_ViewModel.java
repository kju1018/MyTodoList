package com.kmsapp.mytodolist.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;

import java.util.ArrayList;

public class Todo_Today_ViewModel extends ViewModel {
    private FireBaseRepository fireBaseRepository;

    MutableLiveData<ArrayList<Todo>> liveDatas;

    public void init(UserView userView) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.setUserView(userView);
    }

    public LiveData<ArrayList<Todo>> loadTodayTodo(){
        liveDatas = fireBaseRepository.todayTodoLoad();
        return liveDatas;
    }

    public void todoUpload(Todo todo){
        fireBaseRepository.todoUpload(todo);
    }

    public void todoComplete(Todo todo){
        fireBaseRepository.todoComplete(todo);
    }

}
