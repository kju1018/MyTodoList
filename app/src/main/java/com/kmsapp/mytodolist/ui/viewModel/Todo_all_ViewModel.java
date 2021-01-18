package com.kmsapp.mytodolist.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;

public class Todo_all_ViewModel extends ViewModel {

    private FireBaseRepository fireBaseRepository;

    MutableLiveData<ArrayList<Todo>> liveDatas;

    public void init(UserViewListener userViewListener) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.setUserViewListener(userViewListener);
    }

    public LiveData<ArrayList<Todo>> loadAllTodo(){
        liveDatas = fireBaseRepository.allTodoLoad();
        return liveDatas;
    }

    public void todoUpload(Todo todo) {
        fireBaseRepository.todoUpload(todo);
    }

    public void deleteTodo(Todo todo) {
        fireBaseRepository.deleteTodo(todo);
    }
}
