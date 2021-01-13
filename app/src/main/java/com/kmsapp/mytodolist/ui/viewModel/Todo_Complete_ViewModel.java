package com.kmsapp.mytodolist.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kmsapp.mytodolist.model.Todo;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;

import java.util.ArrayList;

public class Todo_Complete_ViewModel extends ViewModel {
    private FireBaseRepository fireBaseRepository;

    MutableLiveData<ArrayList<Todo>> liveDatas;

    public void init() {
        fireBaseRepository = new FireBaseRepository();
    }

    public LiveData<ArrayList<Todo>> loadComplete(){
        liveDatas = fireBaseRepository.completeTodoLoad();
        return liveDatas;
    }

}
