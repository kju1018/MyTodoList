package com.kmsapp.mytodolist.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kmsapp.mytodolist.Interface.FirebaseListener;
import com.kmsapp.mytodolist.Interface.UserViewListener;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment_ViewModel extends ViewModel {

    private FireBaseRepository fireBaseRepository;
    public MutableLiveData<ArrayList<Todo>> todosLive;

    public void init(UserViewListener userViewListener, FirebaseListener listener) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.setUserViewListener(userViewListener);
        fireBaseRepository.setFirebaseListener(listener);
        todosLive = fireBaseRepository.loadSelectTodo(Calendar.getInstance());
    }

    public void loadEvent(){
        fireBaseRepository.loadEvent();
    }


    public void selectDayload(Calendar calendar){
        todosLive = fireBaseRepository.loadSelectTodo(calendar);
    }


}
