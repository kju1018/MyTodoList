package com.kmsapp.mytodolist.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;

public class Todo_all_ViewModel extends ViewModel {

    private FireBaseRepository fireBaseRepository;

    MutableLiveData<ArrayList<Todo>> liveDatas;

    public void init(UserView userView) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.setUserView(userView);
    }

    public LiveData<ArrayList<Todo>> loadAllTodo(){
        liveDatas = fireBaseRepository.allTodoLoad();
        return liveDatas;
    }

}
