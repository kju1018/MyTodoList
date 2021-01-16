package com.kmsapp.mytodolist.ui.viewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applandeo.materialcalendarview.EventDay;
import com.kmsapp.mytodolist.Interface.UserView;
import com.kmsapp.mytodolist.Repository.FireBaseRepository;
import com.kmsapp.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment_ViewModel extends ViewModel {

    private FireBaseRepository fireBaseRepository;

    MutableLiveData<ArrayList<EventDay>> eventsLive;
    public MutableLiveData<ArrayList<Todo>> todosLive;

    public void init(UserView userView) {
        fireBaseRepository = new FireBaseRepository();
        fireBaseRepository.setUserView(userView);
    }

    public LiveData<ArrayList<EventDay>> loadEvent(){
        eventsLive = fireBaseRepository.loadEvent();
        return eventsLive;
    }

    public void selectDayload(Calendar calendar){
        todosLive = fireBaseRepository.loadSelectTodo(calendar);
    }

    public LiveData<ArrayList<Todo>> selectTodo(){
        Log.d("asdf", "selectTodo: ");
        return todosLive;
    }

}
